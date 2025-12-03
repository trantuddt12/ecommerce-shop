package com.ttl.base.controller;

import com.ttl.base.service.BrandService;
import com.ttl.common.dto.BrandDTO;
import com.ttl.common.exception.BussinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public BrandDTO create(@RequestBody BrandDTO pReq) throws BussinessException{
		return mvBrandService.create(pReq);
	}
	@GetMapping
	@Operation(summary = "Get All Brand")
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
