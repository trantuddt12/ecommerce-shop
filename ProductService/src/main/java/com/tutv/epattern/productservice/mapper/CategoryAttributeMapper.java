package com.tutv.epattern.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tutv.epattern.common.dto.CategoryAttributeDTO;
import com.tutv.epattern.common.request.CategoryAttributeReq;
import com.tutv.epattern.productservice.entities.CategoryAttribute;

@Mapper(componentModel = "spring")
public interface CategoryAttributeMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "category.id", source = "categoryId")
	@Mapping(target = "attributeDef.id", source = "attributeDefId")
	CategoryAttribute reqToEntity(CategoryAttributeReq pReq);
	
//	@Mapping(target = "id", ignore = true)
	@Mapping(target = "category.id", source = "categoryId")
	@Mapping(target = "attributeDef.id", source = "attributeDefId")
	CategoryAttribute ToEntity(CategoryAttributeDTO pReq);
	
	@Mapping(target = "categoryId", source = "category.id")
	@Mapping(target = "attributeDefId", source = "attributeDef.id")
	CategoryAttributeDTO toDto(CategoryAttribute pCateAtt);
	
	@Mapping(target = "categoryId", source = "category.id")
	@Mapping(target = "attributeDefId", source = "attributeDef.id")
	List<CategoryAttributeDTO> toListDto(List<CategoryAttribute> pCateAtt);
}
