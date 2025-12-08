package com.ttl.common.request;

import jakarta.validation.constraints.Size;

public record BrandUpdateRequest (
		
		@Size(max = 50 , min = 2, message = "E124")
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic
){}
