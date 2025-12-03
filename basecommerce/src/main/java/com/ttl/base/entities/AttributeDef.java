package com.ttl.base.entities;

import com.ttl.common.dto.AttributeScope;
import com.ttl.common.dto.AttributeType;
import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attribute_defs",
indexes = {
  @Index(name = AttributeDef.INDEX_ATTRIBUTE_DEF, columnList = AttributeDef.Fields.CODE, unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor 
//@Builder
@DynamicUpdate //chỉ nên dùng khi chỉ update ít fields
@FieldNameConstants
public class AttributeDef extends AbstractEntity {

    public static final String INDEX_ATTRIBUTE_DEF = "idx_attr_code";
	
	@Column(nullable = false, length = 50)
	private String code; //color, size
	
	@Column(nullable = false, unique = true, length = 100)
	private String name; //color
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private AttributeType type;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private AttributeScope scope;
	
	@Column(length = 50)
	private String unit;
	
	@Column(nullable = false)
	private boolean variant; // true = dùng cho ProductVariant, false = Product
	
//	@Builder.Default
	@OneToMany(mappedBy = AttributeValue.Fields.ATTRIBUTE_DEF, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();

}
