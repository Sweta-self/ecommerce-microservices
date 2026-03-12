package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.Product;
import com.ecommerce.order_service.feign.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceClient {

    private final ProductClient productClient;

    @CircuitBreaker(name="productService",fallbackMethod = "productFallback")
    public Product fetchProduct(Long productId){
        return productClient.getProductById(productId);
    }

    public Product productFallback(Long productId,Exception ex){
        System.out.println("product service down");

        Product product=new Product();
        product.setId(productId);
        product.setName("Fallback Product");
        product.setPrice(0);
        return product;
    }
}
