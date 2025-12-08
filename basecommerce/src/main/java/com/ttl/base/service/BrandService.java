package com.ttl.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ttl.base.entities.Brand;
import com.ttl.base.mapper.BrandMapper;
import com.ttl.base.repositories.BrandRepository;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.exception.BussinessException;


@Service
public class BrandService {

	private final BrandRepository mvBrandRepository;
	private final BrandMapper mvMapper;
	
	
	public BrandService(BrandRepository pBrandRepository, BrandMapper pMapper) {
		super();
		this.mvBrandRepository = pBrandRepository;
		mvMapper = pMapper;
	}

	public BrandDTO create(BrandDTO pRequest) throws BussinessException {
		
		if(mvBrandRepository.existsByName(pRequest.getSlug())) {
			throw new BussinessException(String.format("Brand with slug : %d is present", pRequest.getSlug()), ITagCode.DATA_ALREADY_EXISTS, getClass());
		}
		Brand lvBrand = mvMapper.createEntityFrom(pRequest);
//        Brand lvBrand = null;
		Brand rvBrand = mvBrandRepository.save(lvBrand);
		 
		return mvMapper.toDto(rvBrand);
	}
	
	public BrandDTO update(BrandDTO pRequest) throws BussinessException {
		Brand lvBrand = mvBrandRepository.findById(pRequest.getId()).orElseThrow(
				() -> new BussinessException(String.format("Brand with id : %d", pRequest.getId()), ITagCode.DATA_NOT_FOUND, getClass()));
//không update slug
//		if(mvBrandRepository.existsBySlugAndIdNot(pRequest.getSlug(), pRequest.getId())) {
//			throw new BussinessException(String.format("Brand with slug : %d is present", pRequest.getSlug()), ITagCode.DATA_ALREADY_EXISTS, getClass());
//		}
		
//		mvMapper.updateEntityFromDto(pRequest, lvBrand);
		Brand rvBrand = mvBrandRepository.save(lvBrand);
		
		return mvMapper.toDto(rvBrand);
	}
	
	public BrandDTO findById(Long pId) throws BussinessException {
		Brand lvBrand = mvBrandRepository.findById(pId).orElseThrow(
				() -> new BussinessException(String.format("Brand with id : %d is not present", pId), ITagCode.DATA_ALREADY_EXISTS, getClass()));
		
		return mvMapper.toDto(lvBrand);
	}
	public List<BrandDTO> findAll() throws BussinessException {
		List<Brand> lvBrands = mvBrandRepository.findAll();
		return mvMapper.toList(lvBrands);
	}
	
	public void deleteById(Long pId) {
		mvBrandRepository.deleteById(pId);
	}
}
