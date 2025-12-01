package com.tutv.epattern.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutv.epattern.productservice.entities.ProductImage;

//@RepositoryRestResource(path = "product-images")
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
