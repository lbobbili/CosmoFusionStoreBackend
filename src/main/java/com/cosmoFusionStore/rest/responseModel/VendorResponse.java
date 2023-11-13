package com.cosmoFusionStore.rest.responseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {

    private String vendorId;
    private String vendorCompany;
    private String vendorName;
    private String email;
    private String brandName;
    private String status;
}
