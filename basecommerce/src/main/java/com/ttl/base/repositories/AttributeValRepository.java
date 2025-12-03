package com.ttl.base.repositories;

import com.ttl.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.AttributeValue;

@Repository
public interface AttributeValRepository extends BaseRepository<AttributeValue> {
//	List<AttributeValue> findAllBy
}
