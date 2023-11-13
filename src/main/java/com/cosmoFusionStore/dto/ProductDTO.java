package com.cosmoFusionStore.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductDTO {



        private int productId;


        private String productName;


        private String productCategory;


        private String productStock;

        private double productPrice;

        private String productDescription;

        private String productImage;

        private int vendorId;

        private String registrationStatus;

        private String vendorName;

        private String brandName;


}
