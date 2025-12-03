package com.ttl.base.controller;

import com.ttl.base.service.AttributeValService;
import com.ttl.common.dto.AttributeValueDTO;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.request.AttributeValReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attval")
public class AttributeValueController {

    @Resource
    private AttributeValService mvAttributeValService;


	@PostMapping
	public AttributeValueDTO create(@RequestBody AttributeValReq pRequest) throws BussinessException{
		return mvAttributeValService.create(pRequest);
	}
	@GetMapping
	public List<AttributeValueDTO> getAll(){
		return mvAttributeValService.getAll();
	}
	@GetMapping("/{pId}")
	@Operation(summary = "Get AttributeVal by id")
	public AttributeValueDTO getById(
			@Parameter(description = "ID of AttributeVal", required = true)
			@PathVariable Long pId) throws BussinessException{
		return mvAttributeValService.getById(pId);
	}
	
	@PatchMapping("/{pId}")
	public AttributeValueDTO update(@PathVariable Long pId, @RequestBody AttributeValReq pReq) throws BussinessException{
		return mvAttributeValService.update(pId, pReq);
	}
	
	@DeleteMapping("/{pId}")
	public void deletebyId(@PathVariable Long pId) throws BussinessException{
		mvAttributeValService.deleteById(pId);
	}
}
