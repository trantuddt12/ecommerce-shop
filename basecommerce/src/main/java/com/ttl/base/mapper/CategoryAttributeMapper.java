package com.ttl.base.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ttl.base.entities.AttributeDef;
import com.ttl.base.entities.Category;
import com.ttl.base.entities.CategoryAttribute;
import com.ttl.common.dto.CategoryAttributeDTO;
import com.ttl.common.request.CategoryAttributeReq;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CategoryAttributeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(toCategory(pReq.getCategoryId()))")
    @Mapping(target = "attributeDef", expression = "java(toAttributeDef(pReq.getAttributeDefId()))")
    CategoryAttribute reqToEntity(CategoryAttributeReq pReq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(toCategory(pReq.getCategoryId()))")
    @Mapping(target = "attributeDef", expression = "java(toAttributeDef(pReq.getAttributeDefId()))")
    CategoryAttribute toEntity(CategoryAttributeDTO pReq);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "attributeDefId", source = "attributeDef.id")
    CategoryAttributeDTO toDto(CategoryAttribute entity);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "attributeDefId", source = "attributeDef.id")
    List<CategoryAttributeDTO> toListDto(List<CategoryAttribute> list);

    // ===== helper =====

    default Category toCategory(Long id) {
        if (id == null) return null;
        Category c = new Category();
        c.setId(id);
        return c;
    }

    default AttributeDef toAttributeDef(Long id) {
        if (id == null) return null;
        AttributeDef a = new AttributeDef();
        a.setId(id);
        return a;
    }
}
