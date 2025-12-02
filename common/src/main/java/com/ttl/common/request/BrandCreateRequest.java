package com.ttl.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//@Getter
//@Setter
public record BrandCreateRequest (
		
		@NotBlank
		@Size(max = 50)
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic,
		
		@NotBlank
		@Size(max = 100)
		String slug
){}

