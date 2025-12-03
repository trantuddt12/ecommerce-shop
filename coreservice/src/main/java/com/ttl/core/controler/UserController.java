package com.ttl.core.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.core.entities.User;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.RegisterRequest;
import com.ttl.core.request.UpdateUserRequest;
import com.ttl.core.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository mvUserRepository;
	
	@Autowired
	private UserService mvUserService;
	
	@PostMapping("")
	public User addUser(@RequestBody RegisterRequest pUser) throws BussinessException {
		return mvUserService.register(pUser);
	}
	
	@PostMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest pUser) throws UserNotFoundException {
		User lvUser = mvUserService.updateUser(pUser);
		return ResponseEntity.ok(lvUser);
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
	@PreAuthorize("hasAuthority('PRODUCT_VIEW')")
	public ResponseEntity<List<User>> getAll(HttpServletRequest request){
		
		if (request.getCookies() != null) {
	        for (jakarta.servlet.http.Cookie c : request.getCookies()) {
	            System.out.println("🍪 Cookie get all : " + c.getName() + " = " + c.getValue());
	        }
	    }
		List<User> lvUsers = mvUserRepository.findAll();
		return ResponseEntity.ok(lvUsers);
	}
}
