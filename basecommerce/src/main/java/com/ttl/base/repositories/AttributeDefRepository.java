package com.ttl.base.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.AttributeDef;

@Repository
public interface AttributeDefRepository extends JpaRepository<AttributeDef, Long> {

//	@Query("select * from ")
//	AttributeDef findAttributeDefById(@Param("id") Long id);
}
