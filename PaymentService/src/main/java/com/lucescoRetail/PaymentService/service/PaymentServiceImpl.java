package com.lucescoRetail.PaymentService.service;

import com.lucescoRetail.PaymentService.entity.TransactionalDetails;
import com.lucescoRetail.PaymentService.model.PaymentMode;
import com.lucescoRetail.PaymentService.model.PaymentRequest;
import com.lucescoRetail.PaymentService.model.PaymentResponse;
import com.lucescoRetail.PaymentService.repository.TransactionalDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{


    @Autowired
    private TransactionalDetailsRepository transactionalDetailsRepository;
    @Override
    public Long doPayment(PaymentRequest paymentRequest) {

        TransactionalDetails transactionalDetails = TransactionalDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().toString())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getRefernceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionalDetailsRepository.save(transactionalDetails);

        log.info("Transaction got completed with id : {}",transactionalDetails.getId());
        return transactionalDetails.getId();



    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the orderID : {}",orderId);

        TransactionalDetails transactionalDetails = transactionalDetailsRepository.findByOrderId(Long.valueOf(orderId));

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionalDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionalDetails.getPaymentMode()))
                .paymentDate(transactionalDetails.getPaymentDate())
                .orderId(transactionalDetails.getOrderId())
                .paymentStatus(transactionalDetails.getPaymentStatus())
                .amount(transactionalDetails.getAmount())
                .build();

        return paymentResponse;


    }
}
