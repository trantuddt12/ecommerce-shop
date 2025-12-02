package com.ttl.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BrandDTO {
	
    private Long id;

    private String name;

    private String description;

    private boolean generic;
    
    private String slug;
}

