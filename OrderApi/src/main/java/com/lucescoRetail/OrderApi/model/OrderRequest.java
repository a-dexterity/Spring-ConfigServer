package com.lucescoRetail.OrderApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private long productId;
    private long totalAmount;
    private long quantity;

    private PaymentMode paymentMode;
}
