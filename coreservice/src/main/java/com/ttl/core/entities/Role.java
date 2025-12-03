package com.ttl.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
	
	@Builder.Default
	@ManyToMany(mappedBy = User.Fields.ROLES)
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
