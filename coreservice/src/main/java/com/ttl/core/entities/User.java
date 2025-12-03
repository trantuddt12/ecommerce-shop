package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class User extends AbstractEntity {
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;

    @Column
	private String email;

    @Column
	private String phoneNumber;
	
	@Builder.Default
	private String status = "A";
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "scaccount_roles",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	private Set<Role> roles = new HashSet<>();
}
