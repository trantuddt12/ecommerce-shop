package com.ttl.base.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ttl.base.entities.AttributeDef;
import com.ttl.base.entities.Product;
import com.ttl.base.entities.ProductImage;
import com.ttl.base.entities.ProductVariant;
import com.ttl.base.entities.ProductVariantAttribute;
import com.ttl.base.mapper.ProductMapper;
import com.ttl.base.repositories.AttributeDefRepository;
import com.ttl.base.repositories.BrandRepository;
import com.ttl.base.repositories.CategoryRepository;
import com.ttl.base.repositories.ProductRepository;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.ProductDTO;
import com.ttl.common.dto.ProductStatus;
import com.ttl.common.dto.ProductVariantAttributeDTO;
import com.ttl.common.dto.ProductVariantDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.response.ProductResponse;


@Service
public class ProductService {

    private final ProductRepository mvProductRepository;
    private final ProductMapper mvMapper;
    private final CategoryRepository mvCategoryRepository;
    private final BrandRepository mvBrandRepository;
    private final AttributeDefRepository mvAttributeDefRepository;

	public ProductService(ProductRepository mvProductRepository, ProductMapper mvMapper,
			CategoryRepository mvCategoryRepository, BrandRepository mvBrandRepository,
			AttributeDefRepository mvAttributeDefRepository) {
		super();
		this.mvProductRepository = mvProductRepository;
		this.mvMapper = mvMapper;
		this.mvCategoryRepository = mvCategoryRepository;
		this.mvBrandRepository = mvBrandRepository;
		this.mvAttributeDefRepository = mvAttributeDefRepository;
	}

	public List<Product> searchProducts(Long categoryId,
                                        Long brandId,
                                        BigDecimal minPrice,
                                        BigDecimal maxPrice,
                                        String status,
                                        String keyword) {
        Specification<Product> spec = Specification
            .where(ProductSpecification.hasCategory(categoryId))
            .and(ProductSpecification.hasBrand(brandId))
            .and(ProductSpecification.priceBetween(minPrice, maxPrice))
            .and(ProductSpecification.hasStatus(status))
            .and(ProductSpecification.hasKeyword(keyword));

        return mvProductRepository.findAll(spec);
    }
	
	public List<ProductDTO> getAll() {
		List<Product> lvProducts = mvProductRepository.findAll();
		return mvMapper.toListDto(lvProducts);
	}
	
	public ProductDTO getById(Long pId) throws BussinessException {
		Product lvProduct = mvProductRepository.findById(pId)
				.orElseThrow(() ->  new BussinessException(String.format("Product with id : %d not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		return mvMapper.toDto(lvProduct);
	}
    
	@Transactional
    public ProductResponse create(ProductDTO pRequest, List<MultipartFile> pImages) throws BussinessException {
    	
    	//valid category
    	if(!mvCategoryRepository.existsById(pRequest.getCategoryId())) {
    		throw new BussinessException(String.format("Category with id : %s not found", pRequest.getCategoryId()), ITagCode.DATA_NOT_FOUND, getClass());
    	}
    	//valid brand
    	if(!mvBrandRepository.existsById(pRequest.getBrandId())) {
    		throw new BussinessException(String.format("Brand with id : %s not found", pRequest.getBrandId()), ITagCode.DATA_NOT_FOUND, getClass());
    	}
    	//valid attribute
    	for(ProductVariantDTO lvProductVariantDTO : pRequest.getVariants()) {
    		for(ProductVariantAttributeDTO attribute : lvProductVariantDTO.getAttributes()) {
    			if(!mvAttributeDefRepository.existsById(attribute.getAttributeDefId())) {
    	    		throw new BussinessException(String.format("Attribute with id : %s not found", attribute.getAttributeDefId()), ITagCode.DATA_NOT_FOUND, getClass());
    	    	}
    		}
    	}
    	//valid product if exist
    	if(mvProductRepository.existsByName(pRequest.getName())) {
    		throw new BussinessException("Product with name : %s is existed!", ITagCode.DATA_ALREADY_EXISTS, getClass());
    	}
    	
    	// save product with images
    	Product lvProduct = mvMapper.dtoToEntity(pRequest);
    	lvProduct.setStatus(ProductStatus.ACTIVE);
    	if(pImages != null && !pImages.isEmpty()) {
    		List<ProductImage> lvProductImages = new ArrayList<ProductImage>();
    		
    		for(int i= 0; i< pImages.size(); i++ ) {
    			MultipartFile lvFile = pImages.get(i);
    			String lvUrl = upload(lvFile);
    			ProductImage lvProductImage  =  ProductImage.builder()
    					.url(lvUrl)
    					.thumbnail(i == 0)
    					.product(lvProduct)
    					.build();
    			lvProductImages.add(lvProductImage);
    		}
    		lvProduct.setImages(lvProductImages);
    	}
    	Product lvSaved = mvProductRepository.save(lvProduct);
    	
    	//save ProductVariants 
    	List<ProductVariant> lvProductVariants = new ArrayList<ProductVariant>();
    	for(ProductVariantDTO productVariantDto : pRequest.getVariants() ) {
    		
    		ProductVariant lvProductVariant = mvMapper.toEntityPV(productVariantDto);
    		lvProductVariant.setProduct(lvProduct);
    		
    		List<ProductVariantAttribute> lvPVAs = new ArrayList();
    		for(ProductVariantAttributeDTO lvPVADTO : productVariantDto.getAttributes()) {
    			ProductVariantAttribute lvPVA =  mvMapper.toEntityPVA(lvPVADTO);
    			lvPVA.setVariant(lvProductVariant);
    			AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(lvPVADTO.getAttributeDefId()).get();
    			lvPVA.setAttributeDef(lvAttributeDef);
    			
    			lvPVAs.add(lvPVA);
    		}
    		lvProductVariant.setAttributes(lvPVAs);
    		
    		lvProductVariants.add(lvProductVariant);
    		
    	}
    	lvProduct.setVariants(lvProductVariants);
    	
    	return mvMapper.toResponse(lvSaved);
    }
    
    public String upload(MultipartFile file) {
    	final String lvUploadDir = "uploads";
    	final Path root = Paths.get(lvUploadDir);
    	
    	try {
    		if(!Files.exists(root)) {
    			Files.createDirectories(root);
    		}
    		String lvFilename = UUID.randomUUID() + "-" +  file.getOriginalFilename();
    		Path lvFilePath = root.resolve(lvFilename);
    		Files.copy(file.getInputStream(), lvFilePath, StandardCopyOption.REPLACE_EXISTING);
    		return lvFilePath.toString();
    	}catch (IOException e) {
			throw new RuntimeException("upload file failed" , e);
		}
    }
    
}

