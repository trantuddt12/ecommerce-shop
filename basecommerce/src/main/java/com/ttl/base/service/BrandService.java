package com.ttl.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ttl.base.entities.Brand;
import com.ttl.base.index.BrandIndexerClient;
import com.ttl.base.mapper.BrandMapper;
import com.ttl.base.repositories.BrandRepository;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.BrandCreateRequest;
import com.ttl.common.request.BrandUpdateRequest;


@Service
public class BrandService {

	private final BrandRepository mvBrandRepository;
	private final BrandMapper mvMapper;
	private final BrandIndexerClient mvBrandIndexerClient;
	
	public BrandService(BrandRepository pBrandRepository, BrandMapper pMapper
			, BrandIndexerClient pBrandIndexerClient) {
		super();
		this.mvBrandRepository = pBrandRepository;
		mvMapper = pMapper;
		mvBrandIndexerClient = pBrandIndexerClient;
	}

	public BrandDTO create(BrandCreateRequest pRequest) throws BussinessException {
		
		if(mvBrandRepository.existsByName(pRequest.slug())) {
			throw new BussinessException(String.format("Brand with slug : %s", pRequest.slug()), ITagCode.DATA_ALREADY_EXISTS, getClass());
		}
		Brand lvBrand = mvMapper.createEntityFrom(pRequest);
		Brand rvBrand = mvBrandRepository.save(lvBrand);
		
		//send
		mvBrandIndexerClient.indexBrand(rvBrand);
		return mvMapper.toDto(rvBrand);
	}
	
	public BrandDTO update(Long id, BrandUpdateRequest pRequest) throws BussinessException {
		Brand lvBrand = mvBrandRepository.findById(id).orElseThrow(
				() -> new BussinessException(String.format("Brand with id : %d", id), ITagCode.DATA_NOT_FOUND, getClass()));
//không update slug
//		if(mvBrandRepository.existsBySlugAndIdNot(pRequest.getSlug(), pRequest.getId())) {
//			throw new BussinessException(String.format("Brand with slug : %d is present", pRequest.getSlug()), ITagCode.DATA_ALREADY_EXISTS, getClass());
//		}
		
		mvMapper.updateEntityFromDto(pRequest, lvBrand);
		Brand rvBrand = mvBrandRepository.save(lvBrand);
//		send
		mvBrandIndexerClient.indexBrand(rvBrand);
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
	}
}
