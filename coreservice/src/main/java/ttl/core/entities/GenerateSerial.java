package com.ttl.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "scserial")
@Table(name = "scserial")
@Data
public class GenerateSerial {

	@Id
	private String serialno;
	
	private String description;
	
	@Column(name = "startwith", precision = 16, scale = 0 )
	private BigDecimal  startwith;
	
	@Column(name = "endwith", precision = 16, scale = 0)
	private BigDecimal endwith; 
	
	@Column(name = "lastserial", precision = 16, scale =0)
	private BigDecimal lastserial;

}