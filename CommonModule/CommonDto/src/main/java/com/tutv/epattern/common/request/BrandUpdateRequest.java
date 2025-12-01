package com.tutv.epattern.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandUpdateRequest (
		
		@NotBlank
		Long id,
		
		@NotBlank
		@Size(max = 50)
		String name,
		
		@Size(max = 255)
		String description,
		
		boolean generic
){}
