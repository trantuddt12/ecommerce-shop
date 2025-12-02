package com.ttl.base.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttl.common.dto.ProductStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "scproduct")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
public class Product extends AuditMetadata{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 255)
	private String name;
	
//	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false, precision = 16, scale = 2)
	private BigDecimal price;
	
	@Column(name = "seller_id", nullable = false)
	private Long sellerId;
	
	@Column(name = "category_id", nullable= false )
	private Long categoryId;
	
	@Column(name = "brand_id", nullable = false)
	private Long brandId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProductStatus status;
	
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

//    @Builder.Default
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductAttribute> attributes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();
    
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
