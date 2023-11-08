package com.cosmoFusionStore.serviceexception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String badRequestErrorMsg) {
        super(badRequestErrorMsg);
    }
}
