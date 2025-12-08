package com.ttl.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandUpdateRequest (
		
//		@NotBlank(message = "E123")
		@Size(max = 50, message = "E124")
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic
){}
