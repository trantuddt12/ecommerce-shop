package com.ttl.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandCreateRequest (
		
		@NotBlank(message = "E123")
		@Size(max = 50 , min = 2, message = "E124")
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic,
		
		@NotBlank
		@Size(max = 100)
		String slug
){}

