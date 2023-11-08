package com.cosmoFusionStore.rest.requestModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductsToCartRequest {
    private int productId;
    private String customerId;
    private int quantity;
}
