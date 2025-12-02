package com.ttl.base.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sccategory_attribute",
       uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "attribute_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // category gắn attribute
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // attribute được phép trong category đó
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private AttributeDef attributeDef;

    // flag: attribute này bắt buộc phải nhập không?
    @Column(nullable = false)
    private boolean required;

    // flag: attribute này là filter chính trong search?
    @Column(nullable = false)
    private boolean filterable;

    // flag: attribute này có phải dùng để tạo SKU variant không?
    @Column(nullable = false)
    private boolean variant;
}


