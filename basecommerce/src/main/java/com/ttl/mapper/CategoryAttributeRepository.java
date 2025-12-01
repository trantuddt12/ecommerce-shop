package com.tutv.epattern.productservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutv.epattern.productservice.entities.CategoryAttribute;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long>{

}
