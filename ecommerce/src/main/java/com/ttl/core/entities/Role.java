package com.ttl.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "scrole")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Role {
	
	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	@Builder.Default
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<User> users = new HashSet<User>();
	
//	@Builder.Default
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//			name = "scrole_permissions",
//			joinColumns = @JoinColumn(name = "role_id"),
//			inverseJoinColumns = @JoinColumn(name = "permission_id")
//			)
//	private Set<Permission> permissions = new HashSet<Permission>();
	
}
