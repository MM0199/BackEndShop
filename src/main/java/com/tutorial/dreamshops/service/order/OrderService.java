package com.tutorial.dreamshops.service.order;

import com.tutorial.dreamshops.dto.OrderDto;
import com.tutorial.dreamshops.enums.OrderStatus;
import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.*;
import com.tutorial.dreamshops.repository.OrderRepository;
import com.tutorial.dreamshops.repository.ProductRepository;
import com.tutorial.dreamshops.repository.UserRepository;
import com.tutorial.dreamshops.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(cart.getTotalAmount());
        order.setUser(cart.getUser());
        Order savedOrder = orderRepository.save(order);
        User user = cart.getUser();
        List<Order> orders = user.getOrders();
        orders.add(savedOrder);
        user.setOrders(orders);
        userRepository.save(user);
        cartService.clearCart(cart.getId());
        return convertToDto(savedOrder);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(item -> {
            Product product = item.getProduct();
            product.setInventory(product.getInventory() - item.getQuantity());
            productRepository.save(product);
            return new OrderItem(item.getQuantity(), item.getTotalPrice(), order, product);
        }).toList();
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository
                .findById(orderId).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository
                .findByUserId(userId)
                .stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
