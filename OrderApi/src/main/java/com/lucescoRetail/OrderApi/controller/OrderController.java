package com.lucescoRetail.OrderApi.controller;

import com.lucescoRetail.OrderApi.model.OrderRequest;
import com.lucescoRetail.OrderApi.model.OrderResponse;
import com.lucescoRetail.OrderApi.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("i was here");
        long orderId = orderService.placeOrder(orderRequest);
        log.info("order id : {}",orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
