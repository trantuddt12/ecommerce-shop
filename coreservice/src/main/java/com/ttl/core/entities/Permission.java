package com.ttl.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "permissions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Permission extends AbstractEntity{
	
	private String name;
	
	private String description;
//	@Builder.Default
//	@ManyToMany(mappedBy = "permissions")
//	@JsonIgnore
//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
//	private Set<Role> roles = new HashSet<Role>();
	
}
