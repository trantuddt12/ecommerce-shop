package com.tutv.epattern.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutv.common.util.constant.ApiResponse;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.common.dto.CategoryDTO;
import com.tutv.epattern.common.request.CategoryReq;
import com.tutv.epattern.productservice.response.CategoryUpdateReq;
import com.tutv.epattern.productservice.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {
	
	private final CategoryService mvCategoryService;
	
	public CategoryController(CategoryService pCategoryService) {
		super();
		this.mvCategoryService = pCategoryService;
	}

	@GetMapping()
	public ResponseEntity<ApiResponse<?>> getAll(){
		List<CategoryDTO> lvCategoryDTOs = mvCategoryService.getAll();
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvCategoryDTOs));
	}
	
	@PostMapping()
	public ResponseEntity<ApiResponse<?>> addCategory(@RequestBody CategoryDTO pCategoryReq) throws BussinessException{
		CategoryDTO lvCategoryDTO =  mvCategoryService.create(pCategoryReq);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvCategoryDTO));
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ApiResponse<?>> update(@RequestBody CategoryDTO pCategoryReq) throws BussinessException{
		CategoryDTO lvCategoryDTO =  mvCategoryService.update(pCategoryReq);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvCategoryDTO));
	}
	
	@DeleteMapping()
	public ResponseEntity<ApiResponse<?>> delete(@RequestParam Long pId) throws BussinessException{
		mvCategoryService.delete(null);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, "Delete successfully!"));
	}
	
	@GetMapping("/id")
	public ResponseEntity<ApiResponse<?>> findByID(@RequestParam Long pID) throws BussinessException{
		CategoryDTO lvCategoryDTO = mvCategoryService.getById(pID);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvCategoryDTO));
	}
}
