package com.ecommerce.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @GetMapping("/inventory/check/{productId}/{quantity}")
    boolean checkStock(@PathVariable Long productId,
                       @PathVariable int quantity);
    @PostMapping("/inventory/reduce")
    void reduceStock(
            @RequestParam Long productId,
            @RequestParam int quantity
    );
}
