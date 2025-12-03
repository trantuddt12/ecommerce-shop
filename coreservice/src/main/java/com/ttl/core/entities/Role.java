package com.ttl.core.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "roles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role extends AbstractEntity{
	
	private String name;
	
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();
	
//	@Builder.Default
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//			name = "scrole_permissions",
//			joinColumns = @JoinColumn(name = "role_id"),
//			inverseJoinColumns = @JoinColumn(name = "permission_id")
//			)
//	private Set<Permission> permissions = new HashSet<Permission>();
	
}
