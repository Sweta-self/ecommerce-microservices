package com.ecommerce.inventory_service.Repository;

import com.ecommerce.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Inventory findByProductId(Long productId);
}
