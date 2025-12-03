package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "category_attributes",
       uniqueConstraints = @UniqueConstraint(columnNames = {CategoryAttribute.Fields.CATEGORY, CategoryAttribute.Fields.ATTRIBUTE_DEF}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldNameConstants
public class CategoryAttribute extends AbstractEntity {

    // category gắn attribute
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Fields.CATEGORY, nullable = false)
    private Category category;

    // attribute được phép trong category đó
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Fields.ATTRIBUTE_DEF, nullable = false)
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


