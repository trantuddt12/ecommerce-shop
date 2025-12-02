package com.ttl.base.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ttl.base.entities.Category;
import com.ttl.base.entities.CategoryAttribute;
import com.ttl.common.dto.CategoryAttributeDTO;
import com.ttl.common.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	@Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "categories", target = "childrenIds")
	@Mapping(source = "attributes", target = "attributes")
    CategoryDTO toDto(Category category);
	
	@Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "attributeDefId", source = "attributeDef.id")
    CategoryAttributeDTO entityToCategoryAttributeDto(CategoryAttribute entity);

    List<CategoryDTO> toDtoList(List<Category> categories);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attributeDef.id", source = "attributeDefId")
    CategoryAttribute dtoToCategoryAttribute(CategoryAttributeDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attributeDef", ignore = true)
    void updateCategoryAttributeByDto(CategoryAttributeDTO dto, @MappingTarget CategoryAttribute entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true) // bỏ parent.id đi
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    Category createDtoToEntity(CategoryDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    void updateByDto(CategoryDTO dto, @MappingTarget Category entity);
    

    @AfterMapping
    default void handleParent(CategoryDTO dto, @MappingTarget Category category) {
        if (dto.getParentId() != null && dto.getParentId() > 0) {
            Category parent = new Category();
            parent.setId(dto.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
    }

    // 👇 Custom mapping method: Set<Category> -> List<Long>
    default List<Long> map(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                         .map(Category::getId)
                         .toList();
    }
    
    
}
