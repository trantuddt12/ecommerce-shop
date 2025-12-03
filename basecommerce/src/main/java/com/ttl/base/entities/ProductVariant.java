package com.ttl.base.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_variants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor 
@Builder
public class ProductVariant extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 100)
    private String sku; // Mã SKU riêng

    @Column(nullable  = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQty;
    
 // Khóa tổ hợp variant để đảm bảo uniqueness (Color=Black|Storage=128GB → băm)
    @Column(name="variant_key_hash", nullable=false, length=64)
    private String variantKeyHash;

 // Map variant -> value (ví dụ: Size = M, Color = Red)
    @Builder.Default
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantAttribute> attributes = new ArrayList<>();
}
