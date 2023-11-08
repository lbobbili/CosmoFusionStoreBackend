package com.cosmoFusionStore.controller;

import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.ProductDetails;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadProducts(@RequestBody ProductDetails productDetails) {
        CommonResponse commonResponse = this.productService.addProducts(productDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/retrieve")
    public ResponseEntity<List<Product>> retrieveProducts(@RequestParam(required = false) String vendorId) {
        List<Product> productList = this.productService.retrieveProductDetails(vendorId);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/retrieve-pending")
    public ResponseEntity<List<Product>> retrieveProducts() {
        List<Product> productList = this.productService.retrievePendingProductDetails();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PutMapping("/status")
    public ResponseEntity<CommonResponse> updateProductStatus(@RequestBody ProductRequest productRequest) {
        CommonResponse commonResponse = this.productService.updateProductStatus(productRequest);
        if(commonResponse.getStatus().equals("SUCCESS")) return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(commonResponse);
    }
}
