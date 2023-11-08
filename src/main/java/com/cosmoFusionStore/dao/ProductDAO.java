package com.cosmoFusionStore.dao;

import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;

import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    List<Product> retrieveProductsByVendorId(String vendorId);
    List<Product> retrieveProductsByStatus(String status);
    int updateProductStatus (ProductRequest productRequest);
    Product retrieveProductById(int productId);
}
