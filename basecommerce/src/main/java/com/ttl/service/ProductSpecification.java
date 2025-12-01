package com.tutv.epattern.productservice.service;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.tutv.epattern.productservice.entities.Product;

public class ProductSpecification {

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) ->
            categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasBrand(Long brandId) {
        return (root, query, cb) ->
            brandId == null ? null : cb.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("price"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            } else if (max != null) {
                return cb.lessThanOrEqualTo(root.get("price"), max);
            } else {
                return null;
            }
        };
    }

    public static Specification<Product> hasStatus(String status) {
        return (root, query, cb) ->
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) ->
            keyword == null ? null :
            cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}

