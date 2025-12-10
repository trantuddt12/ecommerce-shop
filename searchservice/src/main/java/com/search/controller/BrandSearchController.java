package com.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.search.service.BrandIndexService;

@RestController
@RequestMapping("search/brand")
public class BrandSearchController {

	private final BrandIndexService mvBrandIndexService;

	public BrandSearchController(BrandIndexService mvBrandIndexService) {
		super();
		this.mvBrandIndexService = mvBrandIndexService;
	}
	
	@GetMapping
	public ResponseEntity<String> searchBrands(
			@RequestParam("keyword") String keyword
			,@RequestParam(value = "from", defaultValue = "0") int from
            ,@RequestParam(value = "size", defaultValue = "10") int size
			){
		String result = mvBrandIndexService.searchBrands(keyword, from, size);
		return ResponseEntity.ok(result);
	}
}
