package com.tutv.epattern.common.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDTO {
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
