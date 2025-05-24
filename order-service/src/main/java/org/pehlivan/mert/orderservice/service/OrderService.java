package org.pehlivan.mert.orderservice.service;

import org.pehlivan.mert.orderservice.dto.request.CreateOrderRequestDTO;
import org.pehlivan.mert.orderservice.dto.response.ResponseOrderDTO;
import org.pehlivan.mert.orderservice.model.type.OrderStatus;

public interface OrderService {
    ResponseOrderDTO createOrder(CreateOrderRequestDTO request);
    ResponseOrderDTO getOrder(Long id);
    ResponseOrderDTO updateOrderStatus(Long id, OrderStatus status);
    void cancelOrder(Long id);
} 