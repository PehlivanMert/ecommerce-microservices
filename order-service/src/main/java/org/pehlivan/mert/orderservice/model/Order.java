package org.pehlivan.mert.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.pehlivan.mert.orderservice.model.base.BaseEntity;
import org.pehlivan.mert.orderservice.model.type.OrderStatus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ORDERS")
@Entity
public class Order extends BaseEntity {

    private Long customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected void onCreate() {
        status = OrderStatus.PENDING;
    }
} 