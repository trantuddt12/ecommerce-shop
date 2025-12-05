package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;

@Table(name = "attribute_groups", uniqueConstraints = @UniqueConstraint(
        name = AttributeGroup.UK_ATTRIBUTE_GROUP, columnNames = {AttributeGroup.Fields.CODE}
))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class AttributeGroup extends AbstractEntity {

    public static final String UK_ATTRIBUTE_GROUP = "uk_attribute_group";

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @OneToMany(mappedBy = Attribute.Fields.ATTRIBUTE_GROUP, fetch = FetchType.LAZY)
    private Set<Attribute> attributes = new HashSet<>();
}