package com.tutorial.dreamshops.service.order;

import com.tutorial.dreamshops.dto.OrderDto;
import com.tutorial.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto convertToDto(Order order);
}
