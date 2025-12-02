package com.ttl.common.request;

import com.ttl.common.dto.AttributeScope;
import com.ttl.common.dto.AttributeType;

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
