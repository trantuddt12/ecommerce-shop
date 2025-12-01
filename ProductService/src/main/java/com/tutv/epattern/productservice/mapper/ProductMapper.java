package com.tutv.epattern.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tutv.epattern.common.dto.ProductDTO;
import com.tutv.epattern.common.dto.ProductImageDTO;
import com.tutv.epattern.common.dto.ProductVariantAttributeDTO;
import com.tutv.epattern.common.dto.ProductVariantDTO;
import com.tutv.epattern.common.request.ProductCreateRequest;
import com.tutv.epattern.common.response.ProductResponse;
import com.tutv.epattern.productservice.entities.Product;
import com.tutv.epattern.productservice.entities.ProductImage;
import com.tutv.epattern.productservice.entities.ProductVariant;
import com.tutv.epattern.productservice.entities.ProductVariantAttribute;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Map thẳng các field primitive
    @Mapping(target = "id", ignore = true) // id tự sinh
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "attributes", ignore = true) // xử lý riêng ở @AfterMapping
    Product toEntity(ProductCreateRequest req);
    
 // Map thẳng các field primitive
    @Mapping(target = "id", ignore = true) // id tự sinh
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "attributes", ignore = true) // xử lý riêng ở @AfterMapping | đã có CategoryAttribute làm attribute chung
    Product dtoToEntity(ProductDTO req);

    // Map con
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true) // set lại ở @AfterMapping
    ProductImage toEntityPI(ProductImageDTO dto);
    
    
    @Mapping(target = "id", ignore = true )
    @Mapping(target = "product", ignore = true )
    @Mapping(target = "attributes", ignore = true )
    ProductVariant toEntityPV(ProductVariantDTO dto);
    
    ProductResponse toResponse(Product pProduct);

    @Mapping(target = "variants", ignore = true)
	List<ProductDTO> toListDto(List<Product> lvProducts);
    
    @Mapping(target = "variants", ignore = true)
    ProductDTO toDto(Product pProduct);
    
    @Mapping(target = "variantId", source = "variant.id")
    @Mapping(target = "attributeDefId", source = "attributeDef.id")
    ProductVariantAttributeDTO toProductVariantDTO(ProductVariantAttribute pPVA);
    
    @Mapping(target = "variant.id" , source = "variantId")
    @Mapping(target =  "attributeDef.id", source = "attributeDefId")
    ProductVariantAttribute toEntityPVA(ProductVariantAttributeDTO dto);
	
	@Mapping(target = "productId", source = "product.id")
	ProductImageDTO toDtoPI(ProductImage productImage);
}
