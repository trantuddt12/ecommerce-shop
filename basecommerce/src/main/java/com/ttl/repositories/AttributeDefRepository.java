package com.tutv.epattern.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tutv.epattern.productservice.entities.AttributeDef;

@Repository
public interface AttributeDefRepository extends JpaRepository<AttributeDef, Long> {

//	@Query("select * from ")
//	AttributeDef findAttributeDefById(@Param("id") Long id);
}
