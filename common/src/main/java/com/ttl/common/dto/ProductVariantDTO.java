package com.ttl.common.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductVariantDTO {
	
	private String sku; // Mã SKU sriêng

    private BigDecimal price;

    private Integer stockQty;
    
    private String variantKeyHash;
    
    List<ProductVariantAttributeDTO> attributes = new ArrayList<>();
}
