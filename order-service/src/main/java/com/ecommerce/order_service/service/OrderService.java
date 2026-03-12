package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.Product;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.feign.InventoryClient;
import com.ecommerce.order_service.feign.ProductClient;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final InventoryClient inventoryClient;
    private final InventoryServiceClient inventoryServiceClient;







    public Order createOrder(Long productId,int quantity){
        //product fetch
        Product product=productServiceClient.fetchProduct(productId);
        //stock check
        boolean inStock= inventoryServiceClient.checkInventory(productId,quantity);
        if(!inStock){
            throw new RuntimeException("Product out of stock");
        }
        //Reduce stock
        try {
            inventoryClient.reduceStock(productId, quantity);
            //save order
            Order order = new Order();
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setPrice(product.getPrice() * quantity);
            return orderRepository.save(order);
        }
        catch(Exception ex){
            //compensation transaction
            inventoryClient.restoreStock(productId, quantity);
            throw new RuntimeException("Order failed,stock restored");
        }
    }
}
