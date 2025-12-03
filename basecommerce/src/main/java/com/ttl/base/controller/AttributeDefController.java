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
	public AttributeDefDTO create(@RequestBody AttributeDefReq pRequest){
		return mvAttributeService.create(pRequest);
	}
	@GetMapping
	public List<AttributeDefDTO> getAll(){
		return mvAttributeService.getAll();
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get AttributeDef by id")
	public AttributeDefDTO getById(
			@Parameter(description = "ID of AttributeDef", required = true)
			@PathVariable Long pId) throws BussinessException{
		return mvAttributeService.getById(pId);
	}
	@PatchMapping("/{pId}")
	public AttributeDefDTO update(@PathVariable Long pId, @RequestBody AttributeDefReq pReq) throws BussinessException{
		return mvAttributeService.update(pId, pReq);
	}
	@DeleteMapping("/{pId}")
	public void deleteById(@PathVariable Long pId) throws BussinessException{
		mvAttributeService.deleteById(pId);
	}
}
