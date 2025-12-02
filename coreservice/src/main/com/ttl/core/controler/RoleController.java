package com.ttl.core.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.core.entities.Role;
import com.ttl.core.repository.RoleRepository;
import com.ttl.core.request.RoleRequest;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	private RoleRepository mvRoleRepository;
	
	@PostMapping("")
	public ResponseEntity<Role> createRole(@RequestBody RoleRequest pRoleRequest){
		Role lvRole = Role.builder().id(pRoleRequest.getId()).name(pRoleRequest.getName()).description(pRoleRequest.getDescription()).build();
		return ResponseEntity.ok(mvRoleRepository.save(lvRole));
	}
	@GetMapping("")
	public ResponseEntity<List<Role>> getAll(){
		List<Role> lvRoles = mvRoleRepository.findAll();
		return ResponseEntity.ok(lvRoles);
	}
	@DeleteMapping("/{pRoleId}")
	public ResponseEntity<String> deleteByID(@PathVariable String pRoleId){
		mvRoleRepository.deleteById(pRoleId);
		return ResponseEntity.ok("Deleted role has id : " + pRoleId);
	}
}
