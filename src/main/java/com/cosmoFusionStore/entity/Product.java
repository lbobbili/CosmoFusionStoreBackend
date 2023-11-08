package com.cosmoFusionStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_price")
    private double productPrice;

    @Column(name="product_description")
    private String productDescription;

    @Column(name="product_image")
    private String productImage;

    @Column(name="user_id")
    private String userId;

    @Column(name="registration_status")
    private String registrationStatus;
}
