package com.ecommerce.order_service.service;

import com.ecommerce.order_service.feign.InventoryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryServiceClient {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name="inventoryService",fallbackMethod = "inventoryFallback")
    public boolean checkInventory(Long productId,int quantity){
        return inventoryClient.checkStock(productId, quantity);
    }
    public boolean inventoryFallback(Long productId,int quantity, Exception ex){
        System.out.println("Inventory service down");
        return false;
    }
}
