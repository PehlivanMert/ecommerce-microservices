package org.pehlivan.mert.orderservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pehlivan.mert.orderservice.client.ProductServiceClient;
import org.pehlivan.mert.orderservice.converter.OrderConverter;
import org.pehlivan.mert.orderservice.dto.request.CreateOrderRequestDTO;
import org.pehlivan.mert.orderservice.dto.request.OrderItemDTO;
import org.pehlivan.mert.orderservice.dto.request.UpdateProductRequestDTO;
import org.pehlivan.mert.orderservice.dto.response.ResponseOrderDTO;
import org.pehlivan.mert.orderservice.model.Order;
import org.pehlivan.mert.orderservice.model.OrderItem;
import org.pehlivan.mert.orderservice.model.type.OrderStatus;
import org.pehlivan.mert.orderservice.repository.OrderRepository;
import org.pehlivan.mert.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderConverter orderConverter;

    @Override
    @Transactional
    public ResponseOrderDTO createOrder(CreateOrderRequestDTO request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setItems(new ArrayList<>());

        double totalAmount = 0;

        for (OrderItemDTO itemDto : request.items()) {
            var productResponse = productServiceClient.getProduct(itemDto.productId());
            var product = productResponse.getBody().getData();

            if (product.stock() < itemDto.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + itemDto.productId());
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(itemDto.productId())
                    .quantity(itemDto.quantity())
                    .price(product.price())
                    .build();

            order.getItems().add(orderItem);
            totalAmount += (product.price() * itemDto.quantity());
            
            var updateStockRequest = new UpdateProductRequestDTO(
                itemDto.productId(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(product.stock() - itemDto.quantity()),
                Optional.empty()
            );
            productServiceClient.updateStock(updateStockRequest);
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        return orderConverter.toResponseDTO(order);
    }

    @Override
    public ResponseOrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return orderConverter.toResponseDTO(order);
    }

    @Override
    @Transactional
    public ResponseOrderDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderConverter.toResponseDTO(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Cannot cancel order with status: " + order.getStatus());
        }

        for (OrderItem item : order.getItems()) {
            var productResponse = productServiceClient.getProduct(item.getProductId());
            var currentStock = productResponse.getBody().getData().stock();
            
            var updateStockRequest = new UpdateProductRequestDTO(
                item.getProductId(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(currentStock + item.getQuantity()),
                Optional.empty()
            );
            productServiceClient.updateStock(updateStockRequest);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
} 