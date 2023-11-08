package com.cosmoFusionStore.service;

import com.cosmoFusionStore.acquire.OrderAcquirer;
import com.cosmoFusionStore.acquire.ProductAcquirer;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private ProductAcquirer productAcquirer;

    @Autowired
    private OrderAcquirer orderAcquirer;

    public List<Product> retrieveProducts() {
        return this.productAcquirer.retrieveProductsForCustomer();
    }

    public CommonResponse addProductsToCart(List<AddProductsToCartRequest> addProductsToCartRequest) {
        this.productAcquirer.addProductsToCart(addProductsToCartRequest);
        return new CommonResponse("SUCCESS", "Added Order to the cart");
    }

    public List<Product> retrieveCustomerDetails(String userId) {
        return this.orderAcquirer.retrieveOrdersWithDetails(userId);
    }
}
