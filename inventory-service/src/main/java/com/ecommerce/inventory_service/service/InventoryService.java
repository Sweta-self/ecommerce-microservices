package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.Repository.InventoryRepository;
import com.ecommerce.inventory_service.entity.Inventory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory addInventoryStock(Inventory inventory){
        return inventoryRepository.save(inventory);
    }
    public boolean isInStock(Long productId,int quantity){
        Inventory inventory=
                inventoryRepository.findByProductId(productId);
        return inventory !=null && inventory.getQuantity()>=quantity;
    }
    public void reduceStock(Long productId,int quantity){
        Inventory inventory=
                inventoryRepository.findByProductId(productId);
        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventoryRepository.save(inventory);
    }
    public void restoreStock(Long productId,int quantity){
        Inventory inventory=inventoryRepository.findByProductId(productId);
        inventory.setQuantity(inventory.getQuantity()+quantity);
        inventoryRepository.save(inventory);
    }
}
