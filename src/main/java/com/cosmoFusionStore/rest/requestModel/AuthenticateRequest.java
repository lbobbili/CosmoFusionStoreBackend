package com.cosmoFusionStore.rest.requestModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest {
    private String email;
    private String password;
}
