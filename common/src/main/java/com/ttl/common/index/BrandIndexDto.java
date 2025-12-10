package com.ttl.common.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BrandIndexDto {
    private Long id;
    private String name;
    private String description;
    private boolean generic;
    private String slug;
}

