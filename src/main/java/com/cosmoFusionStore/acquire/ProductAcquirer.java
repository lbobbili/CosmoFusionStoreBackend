package com.cosmoFusionStore.acquire;

import com.cosmoFusionStore.dao.OrderDAO;
import com.cosmoFusionStore.dao.ProductDAO;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.enums.RegistrationStatusEnum;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import com.cosmoFusionStore.rest.requestModel.ProductDetails;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.serviceexception.DatabaseRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductAcquirer {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private OrderDAO orderDAO;
    public void addProductDetails(ProductDetails productDetails) {
        Product product = Product.builder()
                .productName(productDetails.getProductName())
                .productPrice(productDetails.getProductPrice())
                .productDescription(productDetails.getProductDescription())
                .productImage(productDetails.getProductImage())
                .userId(productDetails.getVendorId())
                .registrationStatus(RegistrationStatusEnum.PENDING.getRegistrationType())
                .build();

        this.productDAO.addProduct(product);
    }

    public List<Product> retrieveProducts(String vendorId) {
        return this.productDAO.retrieveProductsByVendorId(vendorId);
    }

    public List<Product> retrievePendingProducts() {
        return this.productDAO.retrieveProductsByStatus(RegistrationStatusEnum.PENDING.getStatus());
    }

    public CommonResponse updateProduct(ProductRequest productRequest) {
        for(RegistrationStatusEnum registrationStatusEnum: RegistrationStatusEnum.values()) {
            if(productRequest.getStatus().equals(registrationStatusEnum.getStatus())) {
                productRequest.setStatus(registrationStatusEnum.getRegistrationType());
                break;
            }
        }
        int affectedRows = this.productDAO.updateProductStatus(productRequest);
        if(affectedRows == 1) return new CommonResponse("SUCCESS", "Successfully updated the DB");
        return new CommonResponse("FAILURE", "Failed to update DB.");
    }

    public List<Product> retrieveProductsForCustomer() {
        List<Product> productList = this.productDAO.retrieveProductsByStatus(RegistrationStatusEnum.ADD_TO_CUSTOMER_VIEW.getRegistrationType());
        if(productList.size() == 0) throw new DatabaseRetrievalException("Failed to retrieve Products.");
        return productList;
    }

    public void addProductsToCart(List<AddProductsToCartRequest> addProductsToCartRequest) {
        this.orderDAO.addProductsToCart(addProductsToCartRequest);
    }
}
