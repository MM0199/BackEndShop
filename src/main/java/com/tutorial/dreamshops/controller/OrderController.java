package com.tutorial.dreamshops.controller;

import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.response.ApiResponse;
import com.tutorial.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.version}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId) {
        try {
            orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/order-id")
    public ResponseEntity<ApiResponse> getOrder(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", orderService.getOrder(orderId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user-id")
    public ResponseEntity<ApiResponse> getUserOrders(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", orderService.getOrdersByUserId(userId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
