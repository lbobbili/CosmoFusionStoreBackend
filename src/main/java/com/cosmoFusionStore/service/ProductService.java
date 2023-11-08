package com.cosmoFusionStore.service;

import com.cosmoFusionStore.acquire.ProductAcquirer;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.ProductDetails;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.validate.ProductDetailsRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    @Autowired
    private ProductDetailsRequestValidation productDetailsRequestValidation;
    @Autowired
    private ProductAcquirer productAcquirer;

    public CommonResponse addProducts(ProductDetails productDetails) {
        this.productDetailsRequestValidation.validateProductDetailsRequest(productDetails);
        this.productAcquirer.addProductDetails(productDetails);
        return new CommonResponse("SUCCESS", "Stored Product Details in DB.");
    }

    public List<Product> retrieveProductDetails(String vendorId) {
        return this.productAcquirer.retrieveProducts(vendorId);
    }

    public List<Product> retrievePendingProductDetails() {
        return this.productAcquirer.retrievePendingProducts();
    }

    public CommonResponse updateProductStatus(ProductRequest productRequest) {
        return this.productAcquirer.updateProduct(productRequest);
    }
}
