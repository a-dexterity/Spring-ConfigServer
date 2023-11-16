package com.lucescoRetail.PaymentService.service;

import com.lucescoRetail.PaymentService.model.PaymentRequest;
import com.lucescoRetail.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);


    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
