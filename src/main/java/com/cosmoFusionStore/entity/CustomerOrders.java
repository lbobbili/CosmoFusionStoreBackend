package com.cosmoFusionStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="customer_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderId;
    @Column(name="customer_id")
    private String customerId;
    @Column(name="product_id")
    private int productId;
    @Column(name="quantity")
    private int quantity;
    @Column(name="status")
    private String status;

}
