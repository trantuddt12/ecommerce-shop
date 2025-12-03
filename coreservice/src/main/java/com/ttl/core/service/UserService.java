package com.ttl.core.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.common.utilities.CoreUtils;
import com.ttl.core.config.JwtTokenService;
import com.ttl.core.entities.Role;
import com.ttl.core.entities.User;
import com.ttl.core.repository.RoleRepository;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.RegisterRequest;
import com.ttl.core.request.UpdateUserRequest;

@Service
public class UserService  {
	
	private final UserRepository mvUserRepository; 
	private final SerialService mvSerialService;
	private final RoleRepository mvRoleRepository;
	private final PasswordEncoder lvEncoder;
	private final ModelMapper mvModelMapper;
	private final JwtTokenService mvJwtTokenService;
	
	public UserService(UserRepository mvUserRepository, SerialService mvSerialService, RoleRepository mvRoleRepository,
			PasswordEncoder lvEncoder, ModelMapper mvModelMapper, JwtTokenService mvJwtTokenService) {
		super();
		this.mvUserRepository = mvUserRepository;
		this.mvSerialService = mvSerialService;
		this.mvRoleRepository = mvRoleRepository;
		this.lvEncoder = lvEncoder;
		this.mvModelMapper = mvModelMapper;
		this.mvJwtTokenService = mvJwtTokenService;
	}

	public User register(RegisterRequest pUserRequest) throws BussinessException {
	    // Lấy ID user mới (nếu bạn dùng serial service)
	    String lvUserId = mvSerialService.getNextSerial(ITag.USERID);

	    // Lấy role từ DB
	    Optional<Role> lvRoleOpt = mvRoleRepository.findById(Long.valueOf(pUserRequest.getRoleId()));
	    if (lvRoleOpt.isEmpty()) {
	        throw new BussinessException("Role not found with ID: " + pUserRequest.getRoleId(), ITagCode.UNKNOWN_ERROR, getClass());
	    }

	    Set<Role> roles = new HashSet<>();
	    roles.add(lvRoleOpt.get());

	    // Tạo user mới
	    User lvUser = User.builder()
	            .username(pUserRequest.getUsername())
	            .password(lvEncoder.encode(pUserRequest.getPassword()))
	            .email(pUserRequest.getEmail())
	            .phoneNumber(pUserRequest.getPhonenumber())
	            .roles(roles)
	            .status("A") // nếu bạn muốn default status
	            .build();

	    // Lưu user vào repository
	    return mvUserRepository.save(lvUser);
	}


	public User updateUser(UpdateUserRequest pUser) throws UserNotFoundException {
		User lvUser = mvUserRepository.findById(Long.valueOf(pUser.getId()))
				.orElseThrow(() -> new UserNotFoundException(pUser.getUsername(),ITagCode.USER_NOT_FOUND, getClass()));
		mvModelMapper.map(pUser, lvUser);
		lvUser.setPassword(lvEncoder.encode(pUser.getPassword()));
		return mvUserRepository.save(lvUser);
	}
	
	public User getUserByUsername(String pUsername) throws UserNotFoundException {
		User lvUser = mvUserRepository.findByUsername(pUsername)
				.orElseThrow(()-> new UserNotFoundException(pUsername, ITagCode.USER_NOT_FOUND, getClass()));
		return lvUser;
	}
	
	public String checkRefreshToken(String pRefreshToken) throws UserNotFoundException {
    	String lvToken = "";
    	if(!CoreUtils.isNullStr(pRefreshToken) && mvJwtTokenService.validateToken(pRefreshToken)) {
    		String lvUsername = mvJwtTokenService.extractUsername(pRefreshToken);
    		User lvUser = getUserByUsername(lvUsername);
    		lvToken = mvJwtTokenService.generateRefreshToken(lvUser);
    		return lvToken;
    	}
    	return lvToken;
    }
}
