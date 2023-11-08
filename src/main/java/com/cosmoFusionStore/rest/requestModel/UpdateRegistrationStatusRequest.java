package com.cosmoFusionStore.rest.requestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRegistrationStatusRequest {
    private String userId;
    private String registrationStatus;
}
