package com.tutv.epattern.productservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scproduct_variant_attribute")
@Getter @Setter
@Builder
public class ProductVariantAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private AttributeDef attributeDef;
    
    @Column(nullable=false, length=200) 
    private String valueText;// ví dụ: "Red", "M", "128GB"

    public static ProductVariantAttribute of(ProductVariant v, AttributeDef a, String value) {
        return ProductVariantAttribute.builder().variant(v).attributeDef(a).valueText(value).build();
    }
}

