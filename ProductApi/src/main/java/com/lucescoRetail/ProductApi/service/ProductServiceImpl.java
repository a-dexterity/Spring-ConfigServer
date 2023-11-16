package com.lucescoRetail.ProductApi.service;

import com.lucescoRetail.ProductApi.entity.Product;
import com.lucescoRetail.ProductApi.exceptions.ProductServiceCustomException;
import com.lucescoRetail.ProductApi.model.ProductRequest;
import com.lucescoRetail.ProductApi.model.ProductResponse;
import com.lucescoRetail.ProductApi.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding Product");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();


        productRepository.save(product);
        log.info("Product Added");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Product searching...");
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductServiceCustomException("product with given id is not present", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        log.info("Product Details Provided");
        return productResponse;

    }

    @Override
    public void reduceQuantity(long productId, long quantity) {

        log.info("Product searching...");
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with the given id not found","PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            log.info("Product's Quantity Is Insufficient");
            throw new ProductServiceCustomException("Product have insufficient quantity","INSUFFICIENT_QUANTITY");
        }
        log.info("Product Updating...");
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity Updated");

    }
}
