package com.tutv.epattern.productservice.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import com.tutv.epattern.common.dto.AttributeScope;
import com.tutv.epattern.common.dto.AttributeType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scattribute_def",
indexes = {
  @Index(name = "idx_attr_code", columnList = "code", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@DynamicUpdate //chỉ nên dùng khi chỉ update ít fields
public class AttributeDef extends AuditMetadata{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Builder.Default
	@OneToMany(mappedBy = "attributeDef", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
	
	public void addAttValue(AttributeValue pAttValue) {
		attributeValues.add(pAttValue);
		pAttValue.setAttributeDef(this);
	}
	
	public void remove(AttributeValue pAttValue) {
		attributeValues.remove(pAttValue);
		pAttValue.setAttributeDef(null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDef other = (AttributeDef) obj;
		return Objects.equals(code, other.code) && Objects.equals(id, other.id);
	}

}
