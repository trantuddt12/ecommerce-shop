package com.tutv.epattern.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tutv.epattern.common.dto.BrandDTO;
import com.tutv.epattern.common.request.BrandCreateRequest;
import com.tutv.epattern.productservice.entities.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    
    BrandDTO toDto(Brand brand);

    Brand toEntity(BrandDTO dto);
    
    @Mapping(target = "id", ignore = true)
    Brand createEntityFrom(BrandDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateEntityFromDto(BrandDTO dto, @MappingTarget Brand brand);

    List<BrandDTO> toList(List<Brand> brands);
    
    BrandCreateRequest toRequest(Brand brand);
    
    @Mapping(target = "id", ignore = true)
    Brand reqToEntity(BrandCreateRequest dto);

    List<BrandCreateRequest> reqToList(List<Brand> brands);
}

