package com.tutv.epattern.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutv.common.util.constant.ApiResponse;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.common.dto.BrandDTO;
import com.tutv.epattern.productservice.service.BrandService;

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
	public ResponseEntity<ApiResponse<?>> create(@RequestBody BrandDTO pReq) throws BussinessException{
		BrandDTO lvBrandDTO = mvBrandService.create(pReq);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvBrandDTO));
	}
	@GetMapping
	@Operation(summary = "Get All Brand")
	public ResponseEntity<ApiResponse<?>> getAll() throws BussinessException{
		List<BrandDTO> lvBrandDTOs = mvBrandService.findAll();
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvBrandDTOs));
	}
	@PutMapping("/update")
	@Operation(summary = "Update Brand")
	public ResponseEntity<ApiResponse<?>> update(@RequestBody BrandDTO pReq) throws BussinessException{
		BrandDTO lvBrandDTO = mvBrandService.update(pReq);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvBrandDTO));
	}
	@GetMapping("/id")
	@Operation(summary = "Get Brand By Brand Id")
	public ResponseEntity<ApiResponse<?>> getById(@RequestParam Long pId) throws BussinessException{
		BrandDTO lvBrandDTO = mvBrandService.findById(pId);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvBrandDTO));
	}
	@DeleteMapping()
	public ResponseEntity<ApiResponse<?>> delete(@RequestParam Long pId) throws BussinessException{
		mvBrandService.deleteById(pId);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, "Brand with id : %d is deleted successfully!"));
	}
}
