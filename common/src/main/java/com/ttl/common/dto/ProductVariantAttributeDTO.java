package com.ttl.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantAttributeDTO {
	private Long id;
	
	private Long variantId;

	private Long attributeDefId;

	private String valueText;// ví dụ: "Red", "M", "128GB"
}
