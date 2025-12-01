package com.tutv.epattern.productservice.mapper;

import org.springframework.stereotype.Component;

@Component
public abstract class ProductMapperDecorator implements ProductMapper {

//    @Autowired
//    private ProductMapper delegate; // chính là ProductMapperImpl do MapStruct generate
//
//    @Autowired
//    private AttributeDefRepository attributeDefRepository;
//
//    @Override
//    public Product toEntity(ProductCreateRequest req) {
//        // gọi delegate để map các field đơn giản + images/variants
//        Product product = delegate.toEntity(req);
//
//        // xử lý attributes thủ công
//        if (req.getAttributes() != null) {
//            List<ProductAttribute> attributes = req.getAttributes().stream()
//                .map(attrDto -> {
//                    AttributeDef def = attributeDefRepository.findById(attrDto.getAttributeDefId())
//                        .orElseThrow(() -> new BussinessException("AttributeDef not found"));
//
//                    ProductAttribute pa = new ProductAttribute();
//                    pa.setProduct(product);
//                    pa.setAttributeDef(def);
//                    pa.setValue(attrDto.getValue());
//                    return pa;
//                })
//                .toList();
//            product.setAttributes(attributes);
//        }
//
//        // set back-reference cho children
//        if (product.getImages() != null) {
//            product.getImages().forEach(img -> img.setProduct(product));
//        }
//        if (product.getVariants() != null) {
//            product.getVariants().forEach(variant -> variant.setProduct(product));
//        }
//
//        return product;
//    }
}

