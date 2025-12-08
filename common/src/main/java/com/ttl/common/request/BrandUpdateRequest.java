package com.ttl.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandUpdateRequest (
		
		@NotBlank
		@Size(max = 50)
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic
){}
