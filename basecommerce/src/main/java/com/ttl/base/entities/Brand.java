package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(
    name = "brands",
    uniqueConstraints = {
        @UniqueConstraint(name = Brand.BRAND_CONSTRAINT_NAME, columnNames = Brand.Fields.SLUG)
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@FieldNameConstants
public class Brand extends AbstractEntity {

    public static final String BRAND_CONSTRAINT_NAME = "uk_brand_slug";

    @Column(length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean generic;
    
    @Column(length = 100, nullable = false)
    private String slug;
}

