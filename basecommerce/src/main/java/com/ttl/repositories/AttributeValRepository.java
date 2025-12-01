package com.tutv.epattern.productservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutv.epattern.productservice.entities.AttributeValue;

@Repository
public interface AttributeValRepository extends JpaRepository<AttributeValue, Long> {
//	List<AttributeValue> findAllBy
}
