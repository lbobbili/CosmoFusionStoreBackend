package com.cosmoFusionStore.daoimpl;

import com.cosmoFusionStore.dao.OrderDAO;
import com.cosmoFusionStore.entity.CustomerOrders;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private static final String RETRIEVE_ORDERS = "FROM CustomerOrders WHERE customerId =: userId";
    @Autowired
    private EntityManager entityManager;
    @Override
    @Transactional
    public void addProductsToCart(List<AddProductsToCartRequest> addProductsToCartRequestList) {
        for(AddProductsToCartRequest addProductsToCartRequest: addProductsToCartRequestList) {
            CustomerOrders order = CustomerOrders.builder()
                    .productId(addProductsToCartRequest.getProductId())
                    .customerId(addProductsToCartRequest.getCustomerId())
                    .quantity(addProductsToCartRequest.getQuantity())
                    .status("ADD_TO_CART")
                    .build();

            this.entityManager.persist(order);
        }
    }

    @Override
    public List<CustomerOrders> customerOrders(String userId) {
        TypedQuery<CustomerOrders> query = entityManager.createQuery(RETRIEVE_ORDERS, CustomerOrders.class);
        query.setParameter("userId", userId);
        List<CustomerOrders> ordersList = query.getResultList();
        return ordersList;
    }
}
