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
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public Order placeOrder(Order order){
        Product product=productClient.getProductById(order.getProductId());
        return orderRepository.save(order);
    }
    public Order createOrder(Long productId,int quantity){
        //product fetch
        Product product=productClient.getProductById(productId);
        //stock check
        boolean inStock= inventoryClient.checkStock(productId,quantity);
        if(!inStock){
            throw new RuntimeException("Product out of stock");
        }
        //Reduce stock
        inventoryClient.reduceStock(productId, quantity);
        //save order
        Order order=new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setPrice(product.getPrice()*quantity);
        return orderRepository.save(order);
    }
}
