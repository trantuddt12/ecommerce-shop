package com.ttl.common.request;

import java.util.ArrayList;
import java.util.List;

import com.ttl.common.dto.CategoryAttributeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class CategoryReq {
	
	private String name;
	
	private String description;
	
	private Long parentId;
	
	private List<CategoryAttributeDTO> attributes = new ArrayList<>();
}
