package org.pehlivan.mert.orderservice.dto.response;

import org.pehlivan.mert.orderservice.dto.request.OrderItemDTO;
import org.pehlivan.mert.orderservice.model.type.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseOrderDTO(
    Long id,
    Long customerId,
    List<OrderItemDTO> items,
    double totalAmount,
    OrderStatus status,
    LocalDateTime createdAt
) {} 