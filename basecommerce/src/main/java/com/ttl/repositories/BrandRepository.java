package com.tutv.epattern.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutv.epattern.productservice.entities.Brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{

	boolean existsByName(@NotBlank @Size(max = 50) String name);
	
	boolean existsBySlugAndIdNot(String slug, Long id);
	
	boolean existsByIdAndName(@NotBlank Long id,  @NotBlank @Size(max = 50) String name);

}
