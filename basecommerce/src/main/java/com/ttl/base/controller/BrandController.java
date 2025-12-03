package com.ttl.base.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.base.service.BrandService;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.exception.BussinessException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/brands")
public class BrandController {

	private final BrandService mvBrandService;

	public BrandController(BrandService pBrandService) {
		super();
		this.mvBrandService = pBrandService;
	}
	
	@PostMapping()
	@Operation(summary = "Create Brand")
	@PreAuthorize("hasAuthority('PRODUCT_VIEW')")
	public BrandDTO create(@RequestBody BrandDTO pReq) throws BussinessException{
		return mvBrandService.create(pReq);
	}
	@GetMapping
	@Operation(summary = "Get All Brand")
//	@PreAuthorize("hasAuthority('PRODUCT_VIEW')")
	public List<BrandDTO> getAll() throws BussinessException{
		return mvBrandService.findAll();
	}
	@PutMapping("/update")
	@Operation(summary = "Update Brand")
	public BrandDTO update(@RequestBody BrandDTO pReq) throws BussinessException{
		return mvBrandService.update(pReq);
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get Brand By Brand Id")
	public BrandDTO getById(@RequestParam Long pId) throws BussinessException{
		return mvBrandService.findById(pId);
	}
	@DeleteMapping()
	public void delete(@RequestParam Long pId) throws BussinessException{
		mvBrandService.deleteById(pId);
	}
}
