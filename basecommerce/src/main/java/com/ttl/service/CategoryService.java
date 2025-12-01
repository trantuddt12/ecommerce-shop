package com.tutv.epattern.productservice.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.common.util.utilities.CoreUtils;
import com.tutv.epattern.common.dto.CategoryAttributeDTO;
import com.tutv.epattern.common.dto.CategoryDTO;
import com.tutv.epattern.productservice.entities.AttributeDef;
import com.tutv.epattern.productservice.entities.Category;
import com.tutv.epattern.productservice.entities.CategoryAttribute;
import com.tutv.epattern.productservice.mapper.CategoryAttributeRepository;
import com.tutv.epattern.productservice.mapper.CategoryMapper;
import com.tutv.epattern.productservice.repositories.AttributeDefRepository;
import com.tutv.epattern.productservice.repositories.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository mvCategoryRepository;
	private final AttributeDefRepository mvAttributeDefRepository;
	private final CategoryMapper mvMapper;
	
	public CategoryService(CategoryRepository mvCategoryRepository,
			CategoryAttributeRepository mvCategoryAttributeRepository, AttributeDefRepository mvAttributeDefRepository,
			CategoryMapper mvMapper) {
		super();
		this.mvCategoryRepository = mvCategoryRepository;
		this.mvAttributeDefRepository = mvAttributeDefRepository;
		this.mvMapper = mvMapper;
	}
	public CategoryDTO create(CategoryDTO req) throws BussinessException {
		
        Category category = mvMapper.createDtoToEntity(req);

        if (req.getParentId() != null && req.getParentId() != 0) {
            Category parent = mvCategoryRepository.findById(req.getParentId())
                    .orElseThrow(() -> new BussinessException(String.format("Category with parent id : %d not found!", req.getParentId()) , ITagCode.DATA_NOT_FOUND, getClass()));
            category.setParent(parent);
        }
        List<CategoryAttribute> lvCategoryAttributes = new ArrayList<CategoryAttribute>();
        List<CategoryAttributeDTO> lvAttributeDTOs =  req.getAttributes();
        for(CategoryAttributeDTO dto : lvAttributeDTOs) {
        	AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(dto.getAttributeDefId())
    				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", dto.getAttributeDefId()), ITagCode.DATA_NOT_FOUND, getClass()));
        	CategoryAttribute lvCategoryAttribute = mvMapper.dtoToCategoryAttribute(dto);
        	lvCategoryAttribute.setAttributeDef(lvAttributeDef);
        	lvCategoryAttribute.setCategory(category);
        	lvCategoryAttributes.add(lvCategoryAttribute);
        }
        category.setAttributes(lvCategoryAttributes);
        if(CoreUtils.isNullStr(category.getSlug())) {
        	category.setSlug(CoreUtils.generateSlug(category.getName()));
        }
        Category saved = mvCategoryRepository.save(category);
        return mvMapper.toDto(saved);
    }
    public CategoryDTO update(CategoryDTO pCategoryReq) throws BussinessException {
    	Category lvCategory = mvCategoryRepository.findByIdWithChildren(pCategoryReq.getId()).orElseThrow(
    			() -> new BussinessException(String.format("Category with id : %d not found!", pCategoryReq.getId()) , ITagCode.DATA_NOT_FOUND, getClass()));

    	if(!Objects.isNull(pCategoryReq.getParentId()) && pCategoryReq.getParentId() != 0){
    		mvCategoryRepository.findById(pCategoryReq.getParentId()).orElseThrow(
        			() -> new BussinessException(String.format("Category with parent id : %d not found!", pCategoryReq.getParentId()) , ITagCode.DATA_NOT_FOUND, getClass()));
    	}
    	
    	Map<Long, CategoryAttribute> lvExistsCategoryAttribute = lvCategory.getAttributes().stream()
    			.filter(attr -> attr.getId() != null)
    			.collect(Collectors.toMap(CategoryAttribute :: getId, Function.identity()));
    	
    	mvMapper.updateByDto(pCategoryReq, lvCategory);
    	lvCategory.getAttributes().clear();
    	for(CategoryAttributeDTO dto : pCategoryReq.getAttributes()) {
        	AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(dto.getAttributeDefId())
    				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", dto.getAttributeDefId()), ITagCode.DATA_NOT_FOUND, getClass()));
        	CategoryAttribute lvCategoryAttribute = new CategoryAttribute();
        	// update
        	if(dto.getId() != null && lvExistsCategoryAttribute.containsKey(dto.getId())) {
        		lvCategoryAttribute = lvExistsCategoryAttribute.get(dto.getId());
        		mvMapper.updateCategoryAttributeByDto(dto, lvCategoryAttribute);
        	}else { // insert
        		lvCategoryAttribute = mvMapper.dtoToCategoryAttribute(dto);
        		lvCategoryAttribute.setAttributeDef(lvAttributeDef);
            	lvCategoryAttribute.setCategory(lvCategory);
        	}
        	lvCategory.getAttributes().add(lvCategoryAttribute);
        }
    	
//    	lvCategory.setName(pCategoryReq.getName());
//    	lvCategory.setDescription(pCategoryReq.getDescription());
//    	lvCategory.setParent(lvCategoryParent);
    	Category saved = mvCategoryRepository.save(lvCategory);
    	
    	return mvMapper.toDto(saved);
    }
    
    public void delete(Long pId) throws BussinessException {
    	mvCategoryRepository.findById(pId).orElseThrow(
    			() -> new BussinessException(String.format("Category with id : %d not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
    	mvCategoryRepository.deleteById(pId);
    }
    
    public List<CategoryDTO> getAll(){
    	List<Category> lvCategories = mvCategoryRepository.findAll();
    	return mvMapper.toDtoList(lvCategories);
    }
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long pID) throws BussinessException {
    	Category lvCategory = mvCategoryRepository.findByIdWithChildren(pID)
    			.orElseThrow(() -> new BussinessException("Category with ID {} not found!" , ITagCode.DATA_NOT_FOUND, getClass()));
    	return mvMapper.toDto(lvCategory);
    }

    
}
