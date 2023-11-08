package com.cosmoFusionStore.enums;

public enum RegistrationStatusEnum {
    PENDING("PENDING", "pending"),
    APPROVED("APPROVED", "approved"),
    REJECTED("REJECTED", "rejected"),
    ADD_TO_CUSTOMER_VIEW("ADD_TO_CUSTOMER_VIEW", "customerView");

    private final String registrationType;
    private final String status;

    private RegistrationStatusEnum(String registrationType, String status) {
        this.registrationType = registrationType;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public String getRegistrationType() { return registrationType; }
    public static String getRegistrationTypeByValue(String registrationValue) {
        for (RegistrationStatusEnum registrationStatus : RegistrationStatusEnum.values()) {
            if(registrationStatus.getStatus().equals(registrationValue)) {
                return registrationStatus.getRegistrationType();
            }
        }
        return null;
    }
}
