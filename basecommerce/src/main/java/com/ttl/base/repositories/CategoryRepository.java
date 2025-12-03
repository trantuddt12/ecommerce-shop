package com.ttl.base.repositories;

import java.util.List;
import java.util.Optional;

import com.ttl.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ttl.base.entities.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
	@EntityGraph(attributePaths = "categories")
	List<Category> findAll();
	
//	@EntityGraph(attributePaths = "categories")
	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.categories WHERE c.id = :id")
	Optional<Category> findById(@Param("id") Long id);
	
	@Query("""
		       select distinct c
		       from Category c
		       left join fetch c.categories
		       where c.id = :id
		       """)
	Optional<Category> findByIdWithChildren(@Param("id") Long id);
}
