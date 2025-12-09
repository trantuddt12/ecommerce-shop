package com.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/brands")
public class BrandSearchController {
	
//	private final BrandIndexService brandIndexService;
	
//	public ResponseEntity<List<BrandIndex>> search(@RequestParam String q
//			, )
	
	@GetMapping("/health")
	public ResponseEntity<String> health(){
		return ResponseEntity.ok("ok");
	}
}
