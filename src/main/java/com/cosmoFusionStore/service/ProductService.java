package com.cosmoFusionStore.service;

import com.cosmoFusionStore.acquire.ProductAcquirer;
import com.cosmoFusionStore.acquire.VendorAcquirer;
import com.cosmoFusionStore.dto.ProductDTO;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.entity.Vendor;
import com.cosmoFusionStore.rest.requestModel.ProductDetails;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.validate.ProductDetailsRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
    @Autowired
    private ProductDetailsRequestValidation productDetailsRequestValidation;
    @Autowired
    private ProductAcquirer productAcquirer;

    @Autowired
    private VendorAcquirer vendorAcquirer;

    public CommonResponse addProducts(ProductDetails productDetails) {
        this.productDetailsRequestValidation.validateProductDetailsRequest(productDetails);
        this.productAcquirer.addProductDetails(productDetails);
        return new CommonResponse("SUCCESS", "Stored Product Details in DB.");
    }

    public List<Product> retrieveProductDetails(String vendorId) {
        return this.productAcquirer.retrieveProducts(vendorId);
    }

    public List<ProductDTO> retrievePendingProductDetails(String status) {
        List<Product> productDetails =  this.productAcquirer.retrievePendingProducts(status);
        List<ProductDTO> productList = new ArrayList<>();
        for(int i=0;i<productDetails.size();i++){
           if(productDetails.get(i).getVendorId() != 0 ){
              List<Vendor> vendorDetailsList = vendorAcquirer.getVendor(productDetails.get(i).getVendorId());
             String vendorName =  vendorDetailsList.get(0).getVendorName();
               String brandName =  vendorDetailsList.get(0).getBrandName();
             Product product = productDetails.get(i);
               ProductDTO productDTO = ProductDTO.builder()
                       .productCategory(product.getProductCategory())
                       .productDescription(product.getProductDescription())
                       .productImage(product.getProductImage())
                       .productPrice(product.getProductPrice())
                       .productId(product.getProductId())
                       .productName(product.getProductName())
                       .productStock(product.getProductStock())
                       .vendorId(product.getVendorId())
                       .vendorName(vendorName)
                       .brandName(brandName)
                       .registrationStatus(product.getRegistrationStatus())
                       .build();

               productList.add(productDTO);

           }
        }
        return productList;
    }

    public CommonResponse updateProductStatus(ProductRequest productRequest) {
        return this.productAcquirer.updateProduct(productRequest);
    }
}
