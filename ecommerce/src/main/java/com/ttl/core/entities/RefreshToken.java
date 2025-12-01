package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "screfresh_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends AuditMetadata{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userId;
	
	@Column(nullable = false, unique = true)
	private String tokenId;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String tokenValue;
	
	private String deviceId;
	private String ipAddress;
	private String userAgent;
	
	private boolean revoked;
}
