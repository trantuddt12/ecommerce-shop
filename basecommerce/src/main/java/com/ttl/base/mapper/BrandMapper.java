package com.ttl.base.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ttl.base.entities.Brand;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.request.BrandCreateRequest;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    
    BrandDTO toDto(Brand brand);

    Brand toEntity(BrandDTO dto);
    
//    @Mapping(target = "id", ignore = true)
    Brand createEntityFrom(BrandDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateEntityFromDto(BrandDTO dto, @MappingTarget Brand brand);

    List<BrandDTO> toList(List<Brand> brands);
    
    BrandCreateRequest toRequest(Brand brand);
    
//    @Mapping(target = "id", ignore = true)
    Brand reqToEntity(BrandCreateRequest dto);

    List<BrandCreateRequest> reqToList(List<Brand> brands);
}

