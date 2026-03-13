package com.ecommerce.order_service.service;

import com.ecommerce.order_service.feign.InventoryClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryRetryService {

    private final InventoryClient inventoryClient;

    @Retry(name="inventoryRetry",fallbackMethod="inventoryFallback")
    public boolean checkInventory(Long productId,int quantity){
        return inventoryClient.checkStock(productId, quantity);
    }

    public boolean inventoryFallback(Long productId,int quantity,Throwable ex){
        System.out.println("InventoryService down,retry fallback called");
        throw new RuntimeException("Retry failed");
    }
}
