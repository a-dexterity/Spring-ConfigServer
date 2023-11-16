package com.lucescoRetail.PaymentService.repository;

import com.lucescoRetail.PaymentService.entity.TransactionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionalDetailsRepository extends JpaRepository<TransactionalDetails,Long> {

    TransactionalDetails findByOrderId(long orderId);
}

