package com.tutv.epattern.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {

	private long id;
	
	private String url;
	
	private boolean thumbnail;
	
	private Long productId;
}
