package com.ttl.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.core.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
