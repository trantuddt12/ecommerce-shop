package com.ttl.common.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttributeDefDTO {
	
	private Long id;
	
	private String code; //color, size
	
	private String name; //color
	
	private AttributeType type;
	
	private AttributeScope scope;
	
	private String unit;
	
	private boolean variant; // true = dùng cho ProductVariant, false = Product
	
	List<AttributeValueDTO> attributeValues = new ArrayList<AttributeValueDTO>();
}
