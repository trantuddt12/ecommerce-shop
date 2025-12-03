package com.ttl.common.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ttl.common.dto.ProductImageDTO;
import com.ttl.common.dto.ProductStatus;
import com.ttl.common.dto.ProductVariantDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
	
	@NotNull
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private Long sellerId;
	
	private Long categoryId;
	
	private Long brandId;
	
	private ProductStatus status;
	
//    private List<ProductImageDTO> images;
//
//    private List<ProductVariantDTO> variants = new ArrayList<>();
}

