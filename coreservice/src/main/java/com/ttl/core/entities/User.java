package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "scaccount")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AuditMetadata{

	@Id
	private String id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private String email;
	
	private String phonenumber;
	
	@Builder.Default
	private String status = "A";
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "scaccount_roles",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	private Set<Role> roles = new HashSet<Role>();
	
	public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
