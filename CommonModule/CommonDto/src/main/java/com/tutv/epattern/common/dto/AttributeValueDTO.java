package com.tutv.epattern.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttributeValueDTO {
	private Long id;

    private AttributeDefDTO attributeDef;

    private String code;        // "BLACK", "WHITE"
    
    private String displayName; // "Black", "White"
    
    private Integer sortOrder;
}
