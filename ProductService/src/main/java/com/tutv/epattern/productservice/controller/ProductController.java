package com.tutv.epattern.productservice.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutv.common.util.constant.ApiResponse;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.common.dto.ProductDTO;
import com.tutv.epattern.common.request.ProductCreateRequest;
import com.tutv.epattern.common.response.ProductResponse;
import com.tutv.epattern.productservice.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {

	
	private final ProductService mvProductService;
	
	public ProductController(ProductService pProductService) {
		super();
		this.mvProductService = pProductService;
	}

	@GetMapping()
	public ResponseEntity<ApiResponse<?>> getAll(){
		List<ProductDTO> lvProductDTOs = mvProductService.getAll();
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvProductDTOs));
	}
	
	@GetMapping("/{pId}")
	public ResponseEntity<ApiResponse<?>> getById(@PathVariable Long pId) throws BussinessException{
		ProductDTO lvProductDTO = mvProductService.getById(pId);
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvProductDTO));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>>createProduct(
	        @RequestPart("product") String productJson,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images
	) throws JsonProcessingException, BussinessException {
	    // Parse JSON thủ công
	    ObjectMapper mapper = new ObjectMapper();
	    ProductDTO request = mapper.readValue(productJson, ProductDTO.class);
		ProductResponse lvResponse = 	mvProductService.create(request, images );
		return ResponseEntity.ok(ApiResponse.success(ITagCode.SUCCESS, lvResponse));
	}
}
