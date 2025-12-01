package com.tutv.epattern.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutv.common.util.constant.ApiResponse;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.common.dto.CategoryAttributeDTO;
import com.tutv.epattern.common.request.CategoryAttributeReq;
import com.tutv.epattern.productservice.service.CategoryAttributeService;

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
	public ResponseEntity<ApiResponse<?>> create(@RequestBody CategoryAttributeReq pRequest) throws BussinessException{
		CategoryAttributeDTO lvDefDTO = mvCategoryAttributeService.create(pRequest);
		return ResponseEntity.ok(ApiResponse.success("CategoryAttribute is created successfully!", lvDefDTO));
	}
	@GetMapping
	public ResponseEntity<ApiResponse<?>> getAll(){
		List<CategoryAttributeDTO> lvDefDTOs = mvCategoryAttributeService.getAll();
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTOs));
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get CategoryAttribute by id")
	public ResponseEntity<ApiResponse<?>> getById(
			@Parameter(description = "ID of CategoryAttribute", required = true)
			@PathVariable Long pId) throws BussinessException{
		CategoryAttributeDTO lvDefDTO = mvCategoryAttributeService.findById(pId);
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
	}
//	@PatchMapping("/{pId}")
//	public ResponseEntity<ApiResponse<?>> update(@PathVariable Long pId, @RequestBody CategoryAttributeReq pReq) throws BussinessException{
//		CategoryAttributeDTO lvDefDTO =  mvCategoryAttributeService.update(pId, pReq);
//		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
//	}
	@DeleteMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> deletebyId(@PathVariable Long pId) throws BussinessException{
		mvCategoryAttributeService.deleteById(pId);
		return ResponseEntity.ok(ApiResponse.success("CategoryAttribute is deleted successfully!", ITagCode.SUCCESS));
	}
}
