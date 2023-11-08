package com.cosmoFusionStore.validate;

import com.cosmoFusionStore.rest.requestModel.ProductDetails;
import com.cosmoFusionStore.serviceexception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsRequestValidation {

    public void validateProductDetailsRequest(ProductDetails productDetails) {
        if(productDetails == null) {
            throw new BadRequestException("productDetails request object should not be null");
        } else if(productDetails.getProductName() == null || productDetails.getProductName().isBlank()) {
            notNullNorEmptyErrorMsg("productName");
        } else if(productDetails.getProductPrice() == 0.0) {
            throw new BadRequestException("productPrice should not be 0.0");
        } else if(productDetails.getProductDescription() == null || productDetails.getProductDescription().isBlank()) {
            notNullNorEmptyErrorMsg("productDescription");
        } else if(productDetails.getProductImage() == null || productDetails.getProductImage().isBlank()) {
            notNullNorEmptyErrorMsg("productImage");
        }
    }

    private void notNullNorEmptyErrorMsg(String msg) {
        throw new BadRequestException(msg + " should not be null or empty");
    }
}
