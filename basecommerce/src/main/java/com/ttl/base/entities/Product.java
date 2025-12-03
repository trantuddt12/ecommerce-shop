package com.ttl.base.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttl.common.dto.ProductStatus;
import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class Product extends AbstractEntity {

	@Column(nullable = false, length = 255)
	private String name;

    @Column
    private String code;
	
	@Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
	private String description;
	
	@Column(nullable = false, precision = 16, scale = 2)
	private BigDecimal price;
	
	@Column(name = Fields.SELLER_ID, nullable = false)
	private Long sellerId;
	
	@Column(name = Fields.CATEGORY_ID, nullable= false )
	private Long categoryId;
	
	@Column(name = Fields.BRAND_ID, nullable = false)
	private Long brandId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProductStatus status;
	
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Images> images;

//    @Builder.Default
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductAttribute> attributes = new ArrayList<>();

//    @Builder.Default
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductVariant> variants = new ArrayList<>();

 // tiện ích: thêm attribute vào product, đã có categoryAttribute rồi,, thì k cần class này
//    public void addAttribute(AttributeDef attributeDef, String value) {
//        ProductAttribute pa = new ProductAttribute(this, attributeDef, value);
//        this.attributes.add(pa);
//    }
    
    @Override
    public String toString() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
    }
}
