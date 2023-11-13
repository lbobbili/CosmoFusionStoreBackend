package com.cosmoFusionStore.rest.requestModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendorRegistrationRequest {
    private String vendorCompany;
    private String vendorName;
    private String email;
    private String password;
    private String phoneNumber;
    private String brandName;
    private String detailsAboutVendor;
    private String status;
}
