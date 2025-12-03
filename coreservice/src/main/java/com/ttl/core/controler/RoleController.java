package com.ttl.core.controler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.core.entities.Permission;
import com.ttl.core.entities.Role;
import com.ttl.core.repository.PermissionRepository;
import com.ttl.core.repository.RoleRepository;
import com.ttl.core.request.PermissionReq;
import com.ttl.core.request.RoleRequest;


@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleRepository mvRoleRepository;
	
	@Autowired
	private PermissionRepository mvPermissionRepository;

	@PostMapping("")
	public Role createRole(@RequestBody RoleRequest pRoleRequest) throws BussinessException {

	    // Lấy hoặc tạo Permission từ danh sách request
	    Set<Permission> permissions = new HashSet<>();
	    if (pRoleRequest.getPermissions() != null) {
	        for (PermissionReq pr : pRoleRequest.getPermissions()) {
	            Permission permission;
	            if (pr.getId() != null) {
	                // Nếu có ID, lấy từ DB
	                permission = mvPermissionRepository.findById(pr.getId())
	                        .orElseThrow(() ->  new BussinessException("Permission not found with ID: " + pr.getId(), ITagCode.UNKNOWN_ERROR, getClass()));
	            } else {
	                // Nếu không có ID, tạo mới
	                permission = Permission.builder()
	                        .name(pr.getName())
	                        .description(pr.getDescription())
	                        .build();
	                permission = mvPermissionRepository.save(permission);
	            }
	            permissions.add(permission);
	        }
	    }

	    // Tạo Role mới và set permissions
	    Role lvRole = Role.builder()
	            .name(pRoleRequest.getName())
	            .description(pRoleRequest.getDescription())
	            .permissions(permissions)
	            .build();

	    return mvRoleRepository.save(lvRole);
	}

	@GetMapping("")
	public List<Role> getAll(){
		return mvRoleRepository.findAll();
	}
	@DeleteMapping("/{pRoleId}")
	public String deleteByID(@PathVariable Long pRoleId){
		mvRoleRepository.deleteById(pRoleId);
		return "Deleted role has id : " + pRoleId;
	}
}
