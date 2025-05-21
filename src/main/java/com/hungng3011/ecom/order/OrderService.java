package com.hungng3011.ecom.order;

import com.hungng3011.ecom.product.ProductRepository;
import com.hungng3011.ecom.product.ProductVariantRepository;
import com.hungng3011.ecom.user.UserRepository;
import com.hungng3011.ecom.user.User;
import com.hungng3011.ecom.product.Product;
import com.hungng3011.ecom.product.ProductVariant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            ProductVariantRepository productVariantRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public void createOrder(Order order) {
        // Set up User reference
        if (order.getUser() != null && order.getUser().getId() != null) {
            User user = userRepository.findById(order.getUser().getId()).orElse(null);
            order.setUser(user);
        }
        // Set up relationships for order items
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                handleOrderItem(order, item);
            }
        }
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        );
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        );
        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
        // Optionally update user and items if needed
        if (updatedOrder.getUser() != null && updatedOrder.getUser().getId() != null) {
            User user = userRepository.findById(updatedOrder.getUser().getId()).orElse(null);
            existingOrder.setUser(user);
        }
        if (updatedOrder.getItems() != null) {
            existingOrder.getItems().clear();
            for (OrderItem item : updatedOrder.getItems()) {
                handleOrderItem(existingOrder, item);
                existingOrder.getItems().add(item);
            }
        }
        orderRepository.save(existingOrder);
        return existingOrder;
    }

    private void handleOrderItem(Order existingOrder, OrderItem item) {
        item.setOrder(existingOrder);
        if (item.getProduct() != null && item.getProduct().getId() != null) {
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
            );
            item.setProduct(product);
        }
        if (item.getProductVariant() != null && item.getProductVariant().getId() != null) {
            ProductVariant variant = productVariantRepository.findById(item.getProductVariant().getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product variant not found")
            );
            item.setProductVariant(variant);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
