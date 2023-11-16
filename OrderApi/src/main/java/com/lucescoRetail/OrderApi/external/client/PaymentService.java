package com.lucescoRetail.OrderApi.external.client;

import com.lucescoRetail.OrderApi.exception.CustomException;
import com.lucescoRetail.OrderApi.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-API/payment")
public interface PaymentService {

    @PostMapping()
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default  void fallback(Exception e){
        throw new CustomException("Payment Service is not available", "UNAVAILABLE",500);
    }
}
