package com.lucescoRetail.OrderApi.service;

import com.lucescoRetail.OrderApi.model.OrderRequest;
import com.lucescoRetail.OrderApi.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
