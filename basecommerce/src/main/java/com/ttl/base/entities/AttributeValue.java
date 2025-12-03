package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "attribute_values",
       uniqueConstraints = @UniqueConstraint(name= AttributeValue.UK_ATTR_VALUE_CODE, columnNames={AbstractEntity.Fields.ID, AttributeValue.Fields.CODE}),
       indexes = @Index(name= AttributeValue.INDEX_ATTR_VALUE_CODE, columnList=AttributeValue.Fields.ATTRIBUTE_DEF))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldNameConstants
public class AttributeValue extends AbstractEntity {

    public static final String UK_ATTR_VALUE_CODE = "uk_attr_value_code";
    public static final String INDEX_ATTR_VALUE_CODE = "idx_attr_value_attr";

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name=Fields.ATTRIBUTE_DEF, nullable=false)
    private AttributeDef attributeDef;

    @Column(nullable=false, length=100) 
    private String code;
    
    @Column(nullable=false, length=150) 
    private String displayName;
    
    private Integer sortOrder;
}

