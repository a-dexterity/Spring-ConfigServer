package com.lucescoRetail.OrderApi.external.request;

import com.lucescoRetail.OrderApi.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private long orderId;

    private long amount;

    private String refernceNumber;

    private PaymentMode paymentMode;
}
