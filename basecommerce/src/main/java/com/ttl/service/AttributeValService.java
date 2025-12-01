package com.tutv.epattern.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.common.dto.AttributeValueDTO;
import com.tutv.epattern.common.request.AttributeValReq;
import com.tutv.epattern.productservice.entities.AttributeDef;
import com.tutv.epattern.productservice.entities.AttributeValue;
import com.tutv.epattern.productservice.mapper.AttributeValMapper;
import com.tutv.epattern.productservice.repositories.AttributeDefRepository;
import com.tutv.epattern.productservice.repositories.AttributeValRepository;

@Service
public class AttributeValService {

	private final AttributeValRepository mvAttValRepository;
	private final AttributeDefRepository mvAttDefRepository;
	private final AttributeValMapper mvMapper;
	
	public AttributeValService(AttributeValRepository pAttValRepository, AttributeDefRepository pAttDefRepository 
			,AttributeValMapper pMapper
			) {
		super();
		this.mvAttValRepository = pAttValRepository;
		mvAttDefRepository = pAttDefRepository;
		mvMapper= pMapper;
	}
	
	public AttributeValueDTO create(AttributeValReq pReq) throws BussinessException {
		AttributeDef lvAttributeDef = mvAttDefRepository.findById(pReq.getAttributeDefId())
				.orElseThrow(() -> new BussinessException(String.format("AttributeDef with id : %d not found!", pReq.getAttributeDefId()), ITagCode.DATA_NOT_FOUND, getClass()));
		AttributeValue lvAttributeValue = mvMapper.createReqToEntity(pReq);
		lvAttributeValue.setAttributeDef(lvAttributeDef);
		mvAttValRepository.save(lvAttributeValue);
		return mvMapper.toDto(lvAttributeValue);
	}

	public List<AttributeValueDTO> getAll() {
		List<AttributeValue> lvAttDef = mvAttValRepository.findAll();
		return mvMapper.toListDto(lvAttDef);
	}
	
	public AttributeValueDTO getById(Long pId) throws BussinessException {
		AttributeValue lvAttributeVal = mvAttValRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeVal with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		return mvMapper.toDto(lvAttributeVal);
	}
	
	public void deleteById(Long pId) throws BussinessException {
		AttributeValue lvAttributeVal = mvAttValRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeVal with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		mvAttValRepository.delete(lvAttributeVal);
	}
	
	public AttributeValueDTO update(Long pId, AttributeValReq pReq) throws BussinessException {
		AttributeValue lvAttributeVal = mvAttValRepository.findById(pId)
				.orElseThrow(() -> new BussinessException(String.format("AttributeVal with id : %s not found!", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		mvMapper.updateAttDefByReq(pReq, lvAttributeVal);
		AttributeValue lvSaved = mvAttValRepository.save(lvAttributeVal);
		return mvMapper.toDto(lvSaved);
	}
}
