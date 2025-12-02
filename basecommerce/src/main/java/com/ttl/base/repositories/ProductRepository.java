package com.ttl.base.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.Product;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{

	boolean existsByName(@NotNull String name);

}
