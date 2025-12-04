package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screfresh_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken extends AbstractEntity{

    @Column
	private Long userId;
	
//	@Column(nullable = false, unique = true)
//	private String tokenId;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String tokenValue;

    @Column
	private String deviceId;

    @Column
	private String ipAddress;

    @Column
	private String userAgent;
	
	private boolean revoked;
}
