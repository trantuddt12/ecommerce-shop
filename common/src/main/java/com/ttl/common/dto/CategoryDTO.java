package com.ttl.common.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO {
	private long id;
    private String name;
    private String slug;
    private String description;
    private Long parentId;                  // chỉ giữ parentId thay vì object
    private List<Long> childrenIds;     // có thể hiển thị dạng tree
    private List<CategoryAttributeDTO> attributes = new ArrayList<CategoryAttributeDTO>();
}
