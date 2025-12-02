package com.ttl.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ttl.base.entities.AttributeDef;
import com.ttl.base.entities.Category;
import com.ttl.base.entities.CategoryAttribute;
import com.ttl.base.mapper.CategoryAttributeMapper;
import com.ttl.base.mapper.CategoryAttributeRepository;
import com.ttl.base.repositories.AttributeDefRepository;
import com.ttl.base.repositories.CategoryRepository;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.CategoryAttributeDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.CategoryAttributeReq;

@Service
public class CategoryAttributeService {
	private final CategoryAttributeRepository mvCateAttRepo;
	private final CategoryRepository mvCategoryRepository;
	private final AttributeDefRepository mvAttributeDefRepository;
	private final CategoryAttributeMapper mvAttributeMapper;

	public CategoryAttributeService(CategoryAttributeRepository pCateAttRepo, CategoryRepository pCategoryRepository,
			AttributeDefRepository pAttributeDefRepository, CategoryAttributeMapper pAttributeMapper) {
		super();
		this.mvCateAttRepo = pCateAttRepo;
		this.mvCategoryRepository = pCategoryRepository;
		this.mvAttributeDefRepository = pAttributeDefRepository;
		this.mvAttributeMapper = pAttributeMapper;
	}

	public CategoryAttributeDTO create(CategoryAttributeReq pReq) throws BussinessException {
		
		Category lvCategory = mvCategoryRepository.findByIdWithChildren(pReq.getCategoryId())
    			.orElseThrow(() -> new BussinessException(String.format("Category with ID {} not found!", pReq.getCategoryId()) , ITagCode.DATA_NOT_FOUND, getClass()));
		AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(pReq.getAttributeDefId())
				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", pReq.getAttributeDefId()), ITagCode.DATA_NOT_FOUND, getClass()));
		
		CategoryAttribute lvCateAtt =  mvAttributeMapper.reqToEntity(pReq);
		lvCateAtt.setCategory(lvCategory);
		lvCateAtt.setAttributeDef(lvAttributeDef);
		CategoryAttribute lvSaved = mvCateAttRepo.save(lvCateAtt);
		
		return mvAttributeMapper.toDto(lvSaved);
	}
	
	
	public CategoryAttributeDTO findById(Long pId) throws BussinessException {
		CategoryAttribute lvCategoryAttribute = mvCateAttRepo.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("CategoryAttribute with id : %s not found", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		return mvAttributeMapper.toDto(lvCategoryAttribute);
	}
	
	public List<CategoryAttributeDTO> getAll(){
		List<CategoryAttribute> lvCategoryAttributes = mvCateAttRepo.findAll();
		return mvAttributeMapper.toListDto(lvCategoryAttributes);
	}
	
	public void deleteById(Long pId) throws BussinessException {
		CategoryAttribute lvCategoryAttribute = mvCateAttRepo.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("CategoryAttribute with id : %s not found", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		mvCateAttRepo.delete(lvCategoryAttribute);
	}
}
