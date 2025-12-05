package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "attributes", uniqueConstraints =
        @UniqueConstraint(name= Attribute.UK_ATTR_VALUE_CODE, columnNames={Attribute.Fields.ATTRIBUTE_GROUP, Attribute.Fields.CODE}))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Attribute extends AbstractEntity {

    public static final String UK_ATTR_VALUE_CODE = "UK_ATTR_VALUE_CODE";

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private AttributeGroup attributeGroup;
}
