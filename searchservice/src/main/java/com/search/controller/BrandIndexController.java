package com.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.search.service.BrandIndexService;
import com.ttl.common.index.BrandIndexDto;

@RestController
@RequestMapping("/internal/brand")
public class BrandIndexController {
	
	private final BrandIndexService brandIndexService;
	
	public BrandIndexController(BrandIndexService pBrandIndexService) {
		brandIndexService = pBrandIndexService;
	}
	@PostMapping("/index")
	public ResponseEntity<?> index(@RequestBody BrandIndexDto dto){
		brandIndexService.indexBrand(dto);
		return ResponseEntity.ok().build();
	}

}
