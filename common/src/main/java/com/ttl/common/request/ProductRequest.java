package com.ttl.common.request;

import java.math.BigDecimal;
import java.util.List;

import com.ttl.common.dto.ProductImageDTO;
import com.ttl.common.dto.ProductVariantDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String status;

    private Long sellerId;
    private Long categoryId;
    private Long brandId;

    // enrich thêm từ service khác (nếu gọi sang)
//    private String categoryName;
//    private String brandName;
//    private String sellerName;

    private List<ProductImageDTO> images;
    
    private List<ProductVariantDTO> variants;
}
