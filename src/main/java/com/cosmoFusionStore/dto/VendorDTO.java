package com.cosmoFusionStore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorDTO {

        private Integer vendorId;
        private String vendorCompany;
        private String vendorName;
        private String email;
        private String brandName;
        private String status;


}
