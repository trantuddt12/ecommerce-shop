package com.tutv.epattern.common.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttributeValReq {
	
	private Long attributeDefId;

    private String code;        // "BLACK", "WHITE"
    
    private String displayName; // "Black", "White"
    
    private Integer sortOrder;
}
