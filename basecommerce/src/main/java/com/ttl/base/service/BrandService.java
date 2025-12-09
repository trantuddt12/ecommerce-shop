package com.ttl.base.service;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ttl.base.entities.Brand;
import com.ttl.base.mapper.BrandMapper;
import com.ttl.base.repositories.BrandRepository;
import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.event.BrandEvent;
import com.ttl.common.event.EventAction;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.BrandCreateRequest;
import com.ttl.common.request.BrandUpdateRequest;


@Service
public class BrandService {

	private final BrandRepository mvBrandRepository;
	private final BrandMapper mvMapper;
	private final KafkaTemplate<String, Object> mvKafkaTemplate;
	
	public BrandService(BrandRepository pBrandRepository
			, BrandMapper pMapper
			, KafkaTemplate<String, Object> pKafkaTemplate) {
		super();
		this.mvBrandRepository = pBrandRepository;
		this.mvMapper = pMapper;
		this.mvKafkaTemplate = pKafkaTemplate;
	}

	public BrandDTO create(BrandCreateRequest pRequest) throws BussinessException {
		
		if(mvBrandRepository.existsByName(pRequest.slug())) {
			throw new BussinessException(String.format("Brand with slug : %s", pRequest.slug()), ITagCode.DATA_ALREADY_EXISTS, getClass());
		}
		Brand lvBrand = mvMapper.createEntityFrom(pRequest);
		Brand rvBrand = mvBrandRepository.save(lvBrand);
		
		BrandEvent event = BrandEvent.builder()
				.action(EventAction.CREATE)
				.id(rvBrand.getId())
				.name(rvBrand.getName())
				.description(rvBrand.getDescription())
				.generic(rvBrand.isGeneric())
				.slug(rvBrand.getSlug())
				.build();
		
		mvKafkaTemplate.send(ITag.BRAND_TOPIC,rvBrand.getId().toString(), event);
		return mvMapper.toDto(rvBrand);
	}
	
	public BrandDTO update(Long id, BrandUpdateRequest pRequest) throws BussinessException {
		Brand lvBrand = mvBrandRepository.findById(id).orElseThrow(
				() -> new BussinessException(String.format("Brand with id : %d", id), ITagCode.DATA_NOT_FOUND, getClass()));
		
		mvMapper.updateEntityFromDto(pRequest, lvBrand);
		Brand rvBrand = mvBrandRepository.save(lvBrand);
		
		BrandEvent event = BrandEvent.builder()
				.action(EventAction.UPDATE)
				.id(rvBrand.getId())
				.name(rvBrand.getName())
				.description(rvBrand.getDescription())
				.generic(rvBrand.isGeneric())
				.slug(rvBrand.getSlug())
				.build();
		
		mvKafkaTemplate.send(ITag.BRAND_TOPIC,rvBrand.getId().toString(), event);
		return mvMapper.toDto(rvBrand);
	}
	
	public BrandDTO findById(Long pId) throws BussinessException {
		Brand lvBrand = mvBrandRepository.findById(pId).orElseThrow(
				() -> new BussinessException(String.format("Brand with id : %d", pId), ITagCode.DATA_NOT_FOUND, getClass()));
		
		return mvMapper.toDto(lvBrand);
	}
	public List<BrandDTO> findAll() throws BussinessException {
		List<Brand> lvBrands = mvBrandRepository.findAll();
		return mvMapper.toList(lvBrands);
	}
	
	public void deleteById(Long pId) {
		
		mvBrandRepository.deleteById(pId);
		
		BrandEvent event = BrandEvent.builder()
				.action(EventAction.DELETE)
				.id(pId)
				.build();
		
		mvKafkaTemplate.send(ITag.BRAND_TOPIC,pId.toString(), event);
	}
}
