package com.ttl.base.repositories;

import com.ttl.base.entities.Product;
import com.ttl.core.repository.BaseRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

	boolean existsByName(@NotNull String name);

}
