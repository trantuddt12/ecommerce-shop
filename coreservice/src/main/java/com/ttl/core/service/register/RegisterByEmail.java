package com.ttl.core.service.register;

import com.tutv.common.util.constant.ITag;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.coreservice.entities.Role;
import com.tutv.epattern.coreservice.entities.User;
import com.tutv.epattern.coreservice.event.UserRegisteredEvent;
import com.tutv.epattern.coreservice.repository.RoleRepository;
import com.tutv.epattern.coreservice.repository.UserRepository;
import com.tutv.epattern.coreservice.request.RegisterRequest;
import com.tutv.epattern.coreservice.service.OtpRedisService;
import com.tutv.epattern.coreservice.service.SerialService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

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
