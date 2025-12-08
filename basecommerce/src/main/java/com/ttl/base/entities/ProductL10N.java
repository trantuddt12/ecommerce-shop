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
@Table(name = "product_l10n")
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@AssociationOverride(
        name = "object",
        joinColumns = @JoinColumn(name = "product_id")
)
@Getter
@Setter
public class ProductL10N extends AbstractLocalization<Product> {

    //    public static final Locale defaultLocale = Locale.ENGLISH;
    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;
}
