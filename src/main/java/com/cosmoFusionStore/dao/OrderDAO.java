package com.cosmoFusionStore.dao;

import com.cosmoFusionStore.entity.CustomerOrders;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface OrderDAO {
    void addProductsToCart(List<AddProductsToCartRequest> addProductsToCartRequest);
    List<CustomerOrders> customerOrders(String userId);
}
