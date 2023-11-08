package com.cosmoFusionStore.serviceexception;

public class SignupDataAcquirerException extends RuntimeException {

    public SignupDataAcquirerException(String errorMsg) {
        super(errorMsg);
    }
}
