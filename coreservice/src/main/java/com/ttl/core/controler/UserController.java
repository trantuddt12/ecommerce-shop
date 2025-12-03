package com.ttl.core.controler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.common.constant.ApiResponse;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.common.utilities.CoreUtils;
import com.ttl.core.entities.Role;
import com.ttl.core.entities.User;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.UpdateUserRequest;
import com.ttl.core.response.UserResponse;
import com.ttl.core.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserRepository mvUserRepository;
	
	@Autowired
	private UserService mvUserService;
	
//	@PostMapping("")
//	public ResponseEntity<ApiResponse<?>> addUser(@RequestBody RegisterRequest pUser) {
//		User lvUser = mvUserService.register(pUser);
//		return ResponseEntity.ok(ApiResponse.success("", lvUser));
//	}
	
	@PostMapping("/update")
	public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody UpdateUserRequest pUser) throws UserNotFoundException {
		User lvUser = mvUserService.updateUser(pUser);
		return ResponseEntity.ok(ApiResponse.of(true, "Cập nhật thành công!", lvUser));
	}
	
//	@GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
////	@Transactional(readOnly = true)
//	public ResponseEntity<ApiResponse<?>> getUser(@RequestParam String pId) {
//		User lvUser =  mvUserRepository.findUserById(pId);
//		if(!CoreUtils.isNullStr(lvUser)) {
//			UserResponse lvUserRes =  UserResponse.builder()
//					.id(lvUser.getId())
//					.username(lvUser.getUsername())
//					.email(lvUser.getEmail())
//					.phonenumber(lvUser.getPhonenumber())
//					.status(lvUser.getStatus())
//					.roleids(lvUser.getRoles().stream().map(Role::getId).collect(Collectors.toList()))
//					.build();
//			return ResponseEntity.ok(ApiResponse.of(true, " ", lvUserRes));
//		}else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.of(false, "User not found with Id", null));
//		}
//	}
	@GetMapping("")
	public ResponseEntity<ApiResponse<List<User>>> getAll(HttpServletRequest request){
		
		if (request.getCookies() != null) {
	        for (jakarta.servlet.http.Cookie c : request.getCookies()) {
	            System.out.println("🍪 Cookie get all : " + c.getName() + " = " + c.getValue());
	        }
	    }
		List<User> lvUsers = mvUserRepository.findAll();
		return ResponseEntity.ok(ApiResponse.of(true, "", lvUsers));
	}
}
