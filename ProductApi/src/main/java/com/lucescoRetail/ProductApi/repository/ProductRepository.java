package com.lucescoRetail.ProductApi.repository;

import com.lucescoRetail.ProductApi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
