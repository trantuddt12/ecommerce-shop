package com.ttl.base.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.AttributeValue;

@Repository
public interface AttributeValRepository extends JpaRepository<AttributeValue, Long> {
//	List<AttributeValue> findAllBy
}
