package com.ttl.base.entities;

import com.ttl.core.entities.AbstractLocalization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "category_l10n")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldNameConstants
@AssociationOverride(
        name = "object",
        joinColumns = @JoinColumn(name = "category_id")
)
public class CategoryL10n extends AbstractLocalization<Category> {

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;
}