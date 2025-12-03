package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "images")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
public class Image extends AbstractEntity {
	
	private String url;
	
	private boolean thumbnail;
}
