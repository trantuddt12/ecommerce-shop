package com.ttl.base.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "scattribute_value",
       uniqueConstraints = @UniqueConstraint(name="uk_attr_value_code", columnNames={"attribute_id","code"}),
       indexes = @Index(name="idx_attr_value_attr", columnList="attribute_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttributeValue extends AuditMetadata {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="attribute_id", nullable=false)
    private AttributeDef attributeDef;

    @Column(nullable=false, length=100) 
    private String code;        // "BLACK", "WHITE"
    
    @Column(nullable=false, length=150) 
    private String displayName; // "Black", "White"
    
    private Integer sortOrder;
}

