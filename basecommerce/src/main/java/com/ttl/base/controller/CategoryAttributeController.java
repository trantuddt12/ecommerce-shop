package com.ttl.base.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttl.base.service.CategoryAttributeService;
import com.ttl.common.constant.ApiResponse;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.CategoryAttributeDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.CategoryAttributeReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/cateatt")
public class CategoryAttributeController {

	private final CategoryAttributeService mvCategoryAttributeService;
	
	public CategoryAttributeController(CategoryAttributeService pAttributeService) {
		super();
		this.mvCategoryAttributeService = pAttributeService;
	}

	@PostMapping
	public CategoryAttributeDTO create(@RequestBody CategoryAttributeReq pRequest) throws BussinessException {
		return mvCategoryAttributeService.create(pRequest);
	}
	@GetMapping
	public List<CategoryAttributeDTO> getAll(){
		return mvCategoryAttributeService.getAll();
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get CategoryAttribute by id")
	public CategoryAttributeDTO getById(
			@Parameter(description = "ID of CategoryAttribute", required = true)
			@PathVariable Long pId) throws BussinessException{
		return mvCategoryAttributeService.findById(pId);
	}

	@DeleteMapping("/{pId}")
	public void deletebyId(@PathVariable Long pId) throws BussinessException{
		mvCategoryAttributeService.deleteById(pId);
	}
}
