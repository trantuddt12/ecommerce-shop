package com.ttl.core.service.register;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.core.entities.Role;
import com.ttl.core.entities.User;
import com.ttl.core.event.UserRegisteredEvent;
import com.ttl.core.repository.RoleRepository;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.RegisterRequest;
import com.ttl.core.service.OtpRedisService;
import com.ttl.core.service.SerialService;

@Service
public class RegisterByEmail implements RegisterStrategy{

	private final SerialService mvSerialService;
	private final PasswordEncoder mvEncoder;
	private final UserRepository mvUserRepository;
	private final ApplicationEventPublisher mvEventPublisher;
	private final RoleRepository mvRoleRepository;
	private final KafkaTemplate<String, String> mvKafkaTemplate;
	private final OtpRedisService mvOtpRedisService;

	public RegisterByEmail(SerialService pSerialService, PasswordEncoder pEncoder, UserRepository pUserRepository,
			ApplicationEventPublisher pEventPublisher
			, RoleRepository pRoleRepository
			,KafkaTemplate<String, String> pKafkaTemplate
			,OtpRedisService pOtpRedisService
			) {
		super();
		this.mvSerialService = pSerialService;
		this.mvEncoder = pEncoder;
		this.mvUserRepository = pUserRepository;
		this.mvEventPublisher = pEventPublisher;
		this.mvRoleRepository = pRoleRepository;
		this.mvKafkaTemplate = pKafkaTemplate;
		this.mvOtpRedisService = pOtpRedisService;
	}

	@Override
	public User register(RegisterRequest pUserRequest) throws BussinessException {
		
		String lvUserId = mvSerialService.getNextSerial(ITag.USERID);
		String lvUsername = pUserRequest.getEmail().toLowerCase().substring(0, pUserRequest.getEmail().indexOf("@")) + UUID.randomUUID().toString().substring(0, 3);
		String lvRoleRq = Objects.isNull(pUserRequest.getRoleId()) ? "CUSTOMER" : pUserRequest.getRoleId();
		Role lvRole = mvRoleRepository.findById(lvRoleRq)
				.orElseThrow(() -> new BussinessException("Role not found!", ITagCode.DATA_NOT_FOUND, getClass())
						);
		User lvUser = User.builder()
				.id(lvUserId)
				.username(lvUsername)
				.password(mvEncoder.encode(pUserRequest.getPassword()))
				.email(pUserRequest.getEmail())
				.phonenumber("")
				.roles(new HashSet<>(Arrays.asList(lvRole)))
				.status("P")
				.build();
		User rvUser =  mvUserRepository.save(lvUser);
		
		String lvEmailToken = UUID.randomUUID().toString();
		String lvMessage = "{ \"email\":\"" + pUserRequest.getEmail() + "\", \"token\":\"" + lvEmailToken + "\" }";
		mvKafkaTemplate.send(ITag.Kafka_Verification_Email, lvMessage);
		mvOtpRedisService.saveOtp(ITag.EMAIL_VERIFY, pUserRequest.getEmail() , lvEmailToken);
		mvEventPublisher.publishEvent(new UserRegisteredEvent(this, rvUser));
		return rvUser;
	}

	@Override
	public String registerName() {
		// TODO Auto-generated method stub
		return ITag.REGISTER_BY_EMAIL;
	}

}
