package com.ttl.base.mapper;

import com.ttl.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.CategoryAttribute;

@Repository
public interface CategoryAttributeRepository extends BaseRepository<CategoryAttribute> {

}
