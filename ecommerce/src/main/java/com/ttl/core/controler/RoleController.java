package com.ttl.core.controler;

import com.tutv.epattern.coreservice.entities.Role;
import com.tutv.epattern.coreservice.repository.RoleRepository;
import com.tutv.epattern.coreservice.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
