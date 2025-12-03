package com.ttl.base.mapper;

import java.util.List;

import com.ttl.base.entities.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ttl.base.entities.Product;
import com.ttl.base.entities.ProductVariant;
import com.ttl.base.entities.ProductVariantAttribute;
import com.ttl.common.dto.ProductDTO;
import com.ttl.common.dto.ProductImageDTO;
import com.ttl.common.dto.ProductVariantAttributeDTO;
import com.ttl.common.dto.ProductVariantDTO;
import com.ttl.common.request.ProductCreateRequest;
import com.ttl.common.response.ProductResponse;


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
    Image toEntityPI(ProductImageDTO dto);
    
    
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
	ProductImageDTO toDtoPI(Image productImage);

    ProductImageDTO toDtoPI(Image Images);
}
