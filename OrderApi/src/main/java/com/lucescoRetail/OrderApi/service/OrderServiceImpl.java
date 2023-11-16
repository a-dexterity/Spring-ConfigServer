package com.lucescoRetail.OrderApi.service;

import com.lucescoRetail.OrderApi.entity.Order;
import com.lucescoRetail.OrderApi.exception.CustomException;
import com.lucescoRetail.OrderApi.external.client.PaymentService;
import com.lucescoRetail.OrderApi.external.client.ProductService;
import com.lucescoRetail.OrderApi.external.request.PaymentRequest;
import com.lucescoRetail.OrderApi.external.response.PaymentResponse;
import com.lucescoRetail.OrderApi.external.response.ProductResponse;
import com.lucescoRetail.OrderApi.model.OrderRequest;
import com.lucescoRetail.OrderApi.model.OrderResponse;
import com.lucescoRetail.OrderApi.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Create the order entity and save the order status
        //call product api to block the products and reduce the quantity
        //payment service to complete the payment

        log.info("order request: {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("Created")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(order.getAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment is successful and changing the order status to placed");
            orderStatus = "SUCCESS";
        }catch (Exception e){

            log.info("Payment failed and changing the order status to failed");
            orderStatus = "FAILED";

        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("order successfully created with order id : {}",order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("getting order details for the orderid : {}",orderId);

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException("order not found for the order id: "+orderId,"NOT_FOUND",404));

        log.info("Invoking product service to fetch the product for id : {}",order.getProductId());

        ProductResponse productResponse = restTemplate.getForObject("https://PRODUCT-API/product/"+ order.getProductId(), ProductResponse.class);

        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-API/payment/order/"+orderId,PaymentResponse.class);


        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentMode(paymentResponse.getPaymentMode())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentStatus(paymentResponse.getStatus())
                .build();



        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderResponse;
    }


}
