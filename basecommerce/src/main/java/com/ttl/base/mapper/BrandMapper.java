package com.ttl.base.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ttl.base.entities.Brand;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.request.BrandCreateRequest;
import com.ttl.common.request.BrandUpdateRequest;

@Mapper(componentModel = "spring"
		,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrandMapper {
    
    BrandDTO toDto(Brand brand);

    Brand toEntity(BrandDTO dto);
    
//    @Mapping(target = "id", ignore = true)
    Brand createEntityFrom(BrandCreateRequest dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateEntityFromDto(BrandUpdateRequest dto, @MappingTarget Brand brand);

    List<BrandDTO> toList(List<Brand> brands);
    
    BrandCreateRequest toRequest(Brand brand);
    
//    @Mapping(target = "id", ignore = true)
    Brand reqToEntity(BrandCreateRequest dto);

    List<BrandCreateRequest> reqToList(List<Brand> brands);
}

