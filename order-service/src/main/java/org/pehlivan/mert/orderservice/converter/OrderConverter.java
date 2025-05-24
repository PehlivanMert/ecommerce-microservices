package org.pehlivan.mert.orderservice.converter;

import org.pehlivan.mert.orderservice.dto.request.OrderItemDTO;
import org.pehlivan.mert.orderservice.dto.response.ResponseOrderDTO;
import org.pehlivan.mert.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public ResponseOrderDTO toResponseDTO(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemDTO> itemDtos = order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new ResponseOrderDTO(
                order.getId(),
                order.getCustomerId(),
                itemDtos,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
} 