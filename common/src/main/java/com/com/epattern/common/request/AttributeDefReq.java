package com.tutv.epattern.common.request;

import com.tutv.epattern.common.dto.AttributeScope;
import com.tutv.epattern.common.dto.AttributeType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttributeDefReq {
	private String code; //color, size
	
	private String name; //color
	
	private AttributeType type;
	
	private AttributeScope scope;
	
	private String unit;
	
	private boolean variant; // true = dùng cho ProductVariant, false = Product
}
