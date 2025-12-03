package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variant_attributes")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantAttribute extends AbstractEntity {

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

