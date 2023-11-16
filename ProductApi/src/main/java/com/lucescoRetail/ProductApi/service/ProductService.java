package com.lucescoRetail.ProductApi.service;


import com.lucescoRetail.ProductApi.model.ProductRequest;
import com.lucescoRetail.ProductApi.model.ProductResponse;

public interface ProductService {


    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
