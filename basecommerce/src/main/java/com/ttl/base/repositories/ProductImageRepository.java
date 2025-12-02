package com.ttl.base.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.ProductImage;

//@RepositoryRestResource(path = "product-images")
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
