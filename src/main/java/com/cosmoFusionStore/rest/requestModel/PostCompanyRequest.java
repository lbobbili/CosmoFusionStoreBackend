package com.cosmoFusionStore.rest.requestModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCompanyRequest {
    private String companyName;
    private boolean client;
    private boolean implementationPartner;
    private boolean vendor;
}
