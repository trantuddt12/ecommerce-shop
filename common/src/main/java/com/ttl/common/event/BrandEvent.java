package com.ttl.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandEvent {

	private EventAction action;
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private boolean generic;
	
	private String slug;
}
