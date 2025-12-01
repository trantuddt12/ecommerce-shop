package com.tutv.epattern.productservice.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tutv.epattern.common.dto.AttributeValueDTO;
import com.tutv.epattern.common.request.AttributeValReq;
import com.tutv.epattern.productservice.entities.AttributeValue;

@Mapper(componentModel = "spring")
public interface AttributeValMapper {
	
	AttributeValueDTO toDto(AttributeValue entity);
	
	List<AttributeValueDTO> toListDto(List<AttributeValue> entities);
	
	AttributeValue dtoToEntity(AttributeValueDTO dto);
	
	List<AttributeValue> dtoToListEntities(List<AttributeValueDTO> entities);
	
	@Mapping(source = "attributeDef.id", target = "attributeDefId")
	AttributeValReq toCreateReq(AttributeValue entity);
	
	@Mapping(source = "attributeDefId" , target = "attributeDef.id")
	@Mapping(target = "id", ignore = true)
	AttributeValue createReqToEntity(AttributeValReq pReq);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "attributeDefId", target = "attributeDef.id")
	void updateAttDefByReq(AttributeValReq pReq, @MappingTarget AttributeValue lvAttributeVal);
	
}
