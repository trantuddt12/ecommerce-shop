package com.ttl.base.controller;

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

import com.ttl.base.service.AttributeDefService;
import com.ttl.common.constant.ApiResponse;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.dto.AttributeDefDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.AttributeDefReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/attdef")
public class AttributeDefController {

	private final AttributeDefService mvAttributeService;
	
	public AttributeDefController(AttributeDefService pAttributeService) {
		super();
		this.mvAttributeService = pAttributeService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<?>> create(@RequestBody AttributeDefReq pRequest){
		AttributeDefDTO lvDefDTO = mvAttributeService.create(pRequest);
		return ResponseEntity.ok(ApiResponse.success("Attribute is created successfully!", lvDefDTO));
	}
	@GetMapping
	public ResponseEntity<ApiResponse<?>> getAll(){
		List<AttributeDefDTO> lvDefDTOs = mvAttributeService.getAll();
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTOs));
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get AttributeDef by id")
	public ResponseEntity<ApiResponse<?>> getById(
			@Parameter(description = "ID of AttributeDef", required = true)
			@PathVariable Long pId) throws BussinessException{
		AttributeDefDTO lvDefDTO = mvAttributeService.getById(pId);
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
	}
	@PatchMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> update(@PathVariable Long pId, @RequestBody AttributeDefReq pReq) throws BussinessException{
		AttributeDefDTO lvDefDTO =  mvAttributeService.update(pId, pReq);
		return ResponseEntity.ok(ApiResponse.success("", lvDefDTO));
	}
	@DeleteMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> deletebyId(@PathVariable Long pId) throws BussinessException{
		mvAttributeService.deleteById(pId);
		return ResponseEntity.ok(ApiResponse.success("Attribute is deleted successfully!", ITagCode.SUCCESS));
	}
}
