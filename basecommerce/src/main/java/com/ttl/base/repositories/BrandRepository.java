package com.ttl.base.repositories;

import com.ttl.base.entities.Brand;
import com.ttl.core.repository.BaseRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends BaseRepository<Brand> {

	boolean existsByName(@NotBlank @Size(max = 50) String name);
	
	boolean existsBySlugAndIdNot(String slug, Long id);
	
	boolean existsByIdAndName(@NotBlank Long id,  @NotBlank @Size(max = 50) String name);

}
