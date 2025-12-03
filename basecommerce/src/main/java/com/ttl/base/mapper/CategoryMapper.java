package com.ttl.base.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.ttl.base.entities.AttributeDef;
import com.ttl.base.entities.Category;
import com.ttl.base.entities.CategoryAttribute;
import com.ttl.common.dto.CategoryAttributeDTO;
import com.ttl.common.dto.CategoryDTO;
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "categories", target = "childrenIds", qualifiedByName = "mapCategoryIds")
    CategoryDTO toDto(Category category);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "attributeDefId", source = "attributeDef.id")
    CategoryAttributeDTO entityToCategoryAttributeDto(CategoryAttribute entity);

    List<CategoryDTO> toDtoList(List<Category> categories);

    // --- CREATE MAPPING (REQUEST -> ENTITY) ---
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attributeDef", expression = "java(toAttributeDef(dto.getAttributeDefId()))")
    CategoryAttribute dtoToCategoryAttribute(CategoryAttributeDTO dto);

    // --- UPDATE MAPPING ---
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attributeDef", ignore = true)
    void updateCategoryAttributeByDto(CategoryAttributeDTO dto, @MappingTarget CategoryAttribute entity);

    @AfterMapping
    default void fillAfterUpdate(CategoryAttributeDTO dto, @MappingTarget CategoryAttribute entity) {
        if (dto.getAttributeDefId() != null) {
            AttributeDef def = new AttributeDef();
            def.setId(dto.getAttributeDefId());
            entity.setAttributeDef(def);
        }
    }

    // --- CATEGORY CREATE ---
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    Category createDtoToEntity(CategoryDTO dto);

    // --- CATEGORY UPDATE ---
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

    @Named("mapCategoryIds")
    default List<Long> mapCategoryIds(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                         .map(Category::getId)
                         .toList();
    }

    // helper
    default AttributeDef toAttributeDef(Long id) {
        if (id == null) return null;
        AttributeDef def = new AttributeDef();
        def.setId(id);
        return def;
    }
}
