package com.tutv.epattern.productservice.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tutv.epattern.common.dto.AttributeDefDTO;
import com.tutv.epattern.common.request.AttributeDefReq;
import com.tutv.epattern.productservice.entities.AttributeDef;

@Mapper(componentModel = "spring")
public interface AttributeDefMapper {

	@Mapping(target = "attributeValues", ignore = true)
	AttributeDefDTO toDto(AttributeDef attributeDef);
	
	@Mapping(target = "attributeValues", ignore = true)
	List<AttributeDefDTO> toListDto(List<AttributeDef> attributeDefs);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "attributeValues", ignore = true)
	AttributeDef createReqToEntity(AttributeDefReq pRequest);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "attributeValues", ignore = true)
	void updateAttDefByDto(AttributeDefDTO pDto, @MappingTarget AttributeDef pAttDef);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "attributeValues", ignore = true)
	@Mapping(target = "id", ignore = true)
	void updateAttDefByReq(AttributeDefReq pReq, @MappingTarget AttributeDef pAttDef);
	
	@Mapping(target = "attributeValues", ignore = true)
	AttributeDef DtoToEntity(AttributeDefDTO dto);
}
