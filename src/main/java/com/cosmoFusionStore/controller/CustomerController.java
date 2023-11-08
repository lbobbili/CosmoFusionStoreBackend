package com.cosmoFusionStore.controller;

import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.service.CustomerService;
import com.cosmoFusionStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/view-products")
    public ResponseEntity<List<Product>> viewProducts() {
        List<Product> productList = this.customerService.retrieveProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<CommonResponse> addProductsToCart(@RequestBody List<AddProductsToCartRequest> addProductsToCartRequest) {
        CommonResponse commonResponse = this.customerService.addProductsToCart(addProductsToCartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/checkout/{userId}")
    public ResponseEntity<List<Product>> checkout(@PathVariable String userId) {
        List<Product> productList = this.customerService.retrieveCustomerDetails(userId);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
