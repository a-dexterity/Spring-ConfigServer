package com.lucescoRetail.OrderApi.repository;

import com.lucescoRetail.OrderApi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
