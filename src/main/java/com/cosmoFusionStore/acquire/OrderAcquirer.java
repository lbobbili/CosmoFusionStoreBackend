package com.cosmoFusionStore.acquire;

import com.cosmoFusionStore.dao.OrderDAO;
import com.cosmoFusionStore.dao.ProductDAO;
import com.cosmoFusionStore.entity.CustomerOrders;
import com.cosmoFusionStore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderAcquirer {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ProductDAO productDAO;
    public List<Product> retrieveOrdersWithDetails(String userId) {
        List< CustomerOrders> customerOrderList = this.orderDAO.customerOrders(userId);
        List<Product> productList = new ArrayList<>();
        for(CustomerOrders customerOrders: customerOrderList) {
            if(customerOrders.getStatus().equals("ADD_TO_CART")) {
                Product product = this.productDAO.retrieveProductById(customerOrders.getProductId());
                productList.add(product);
            }
        }
        return productList;
    }
}
