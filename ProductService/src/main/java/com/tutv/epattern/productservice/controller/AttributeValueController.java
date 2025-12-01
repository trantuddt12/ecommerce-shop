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
import com.tutv.epattern.common.dto.AttributeValueDTO;
import com.tutv.epattern.common.request.AttributeValReq;
import com.tutv.epattern.productservice.service.AttributeValService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/attval")
public class AttributeValueController {
	private final AttributeValService mvAttributeValService;
	
	public AttributeValueController(AttributeValService pAttributeService) {
		super();
		this.mvAttributeValService = pAttributeService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<?>> create(@RequestBody AttributeValReq pRequest) throws BussinessException{
		AttributeValueDTO lvDTO = mvAttributeValService.create(pRequest);
		return ResponseEntity.ok(ApiResponse.success("Attribute is created successfully!", lvDTO));
	}
	@GetMapping
	public ResponseEntity<ApiResponse<?>> getAll(){
		List<AttributeValueDTO> lvDefDTOs = mvAttributeValService.getAll();
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTOs));
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get AttributeVal by id")
	public ResponseEntity<ApiResponse<?>> getById(
			@Parameter(description = "ID of AttributeVal", required = true)
			@PathVariable Long pId) throws BussinessException{
		AttributeValueDTO lvDefDTO = mvAttributeValService.getById(pId);
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
	}
	
	@PatchMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> update(@PathVariable Long pId, @RequestBody AttributeValReq pReq) throws BussinessException{
		AttributeValueDTO lvDefDTO =  mvAttributeValService.update(pId, pReq);
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
	}
	
	@DeleteMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> deletebyId(@PathVariable Long pId) throws BussinessException{
		mvAttributeValService.deleteById(pId);
		return ResponseEntity.ok(ApiResponse.success("Attribute is deleted successfully!", ITagCode.SUCCESS));
	}
}
