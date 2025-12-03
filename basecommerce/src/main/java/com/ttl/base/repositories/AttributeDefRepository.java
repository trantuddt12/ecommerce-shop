package com.ttl.base.repositories;

import com.ttl.base.entities.AttributeDef;
import com.ttl.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeDefRepository extends BaseRepository<AttributeDef> {

//	@Query("select * from ")
//	AttributeDef findAttributeDefById(@Param("id") Long id);
}
