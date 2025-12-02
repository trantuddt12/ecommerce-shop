package com.ttl.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ttl.base.entities.AttributeDef;
import com.ttl.base.mapper.AttributeDefMapper;
import com.ttl.base.repositories.AttributeDefRepository;
import com.ttl.base.repositories.AttributeValRepository;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.AttributeDefDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.AttributeDefReq;

@Service
public class AttributeDefService {

	private final AttributeDefRepository mvAttributeDefRepository;
	private final AttributeValRepository mvAttributeValRepository;
	private final AttributeDefMapper mvMapper;

	public AttributeDefService(AttributeDefRepository mvAttributeRepository, AttributeDefMapper pMapper, AttributeValRepository pAttributeValRepository) {
		super();
		this.mvAttributeDefRepository = mvAttributeRepository;
		mvMapper = pMapper;
		mvAttributeValRepository = pAttributeValRepository;
	}
	
	public AttributeDefDTO create(AttributeDefReq pRequest) {
		AttributeDef lvAttDef = mvMapper.createReqToEntity(pRequest);
		AttributeDef lvSave = mvAttributeDefRepository.save(lvAttDef);
		return mvMapper.toDto(lvSave);
	}
	
	public List<AttributeDefDTO> getAll() {
		List<AttributeDef> lvAttDef = mvAttributeDefRepository.findAll();
		return mvMapper.toListDto(lvAttDef);
	}
	
	public AttributeDefDTO getById(Long pId) throws BussinessException {
		AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		return mvMapper.toDto(lvAttributeDef);
	}
	
	public void deleteById(Long pId) throws BussinessException {
		AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		mvAttributeDefRepository.delete(lvAttributeDef);
	}

	public AttributeDefDTO update(Long pId, AttributeDefReq pReq) throws BussinessException {
		AttributeDef lvAttributeDef = mvAttributeDefRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		mvMapper.updateAttDefByReq(pReq, lvAttributeDef);
		AttributeDef lvSaved = mvAttributeDefRepository.save(lvAttributeDef);
		return mvMapper.toDto(lvSaved);
	}
}
