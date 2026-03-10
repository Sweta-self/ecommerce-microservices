package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public Inventory addInventory(@RequestBody Inventory inventory)
    {
        return inventoryService.addInventoryStock(inventory);
    }

    @GetMapping("/check/{productId}/{quantity}")
    public boolean checkStock(@PathVariable Long productId,
                              @PathVariable int quantity){
        return inventoryService.isInStock(productId,quantity);
    }

    @PostMapping("/reduce")
    public void reduceStock(@RequestParam Long productId,
                            @RequestParam int quantity){
        inventoryService.reduceStock(productId, quantity);
    }
}
