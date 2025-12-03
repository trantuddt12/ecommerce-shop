package com.ttl.base.controller;

import com.ttl.base.service.CategoryService;
import com.ttl.common.dto.CategoryDTO;
import com.ttl.common.exception.BussinessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService mvCategoryService;
	
	public CategoryController(CategoryService pCategoryService) {
		super();
		this.mvCategoryService = pCategoryService;
	}

	@GetMapping
	public List<CategoryDTO> getAll(){
		return mvCategoryService.getAll();
	}
	
	@PostMapping()
	public CategoryDTO addCategory(@RequestBody CategoryDTO pCategoryReq) throws BussinessException{
		return mvCategoryService.create(pCategoryReq);
	}
	
	@PatchMapping("/update")
	public CategoryDTO update(@RequestBody CategoryDTO pCategoryReq) throws BussinessException{
		return mvCategoryService.update(pCategoryReq);
	}
	
	@DeleteMapping()
	public void delete(@RequestParam Long pId) throws BussinessException{
		mvCategoryService.delete(null);
	}
	
	@GetMapping("/id")
	public CategoryDTO findByID(@RequestParam Long pID) throws BussinessException{
		return mvCategoryService.getById(pID);
	}
}
