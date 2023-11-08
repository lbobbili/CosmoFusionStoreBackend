package com.cosmoFusionStore.enums;

public enum RolesEnum {
    ROLE_ADMIN("1", "ROLE_ADMIN", "Admin"),
    ROLE_VENDOR("2", "ROLE_VENDOR", "Vendor"),
    ROLE_CUSTOMER("3", "ROLE_CUSTOMER", "Customer");

    private final String id;
    private final String role;
    private final String value;

    private RolesEnum(String id, String role, String value) {
        this.value = value;
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }
    public String getValue() {
        return value;
    }
    public String getId() {
        return id;
    }

    public static String getRoleById(String id) {
        for (RolesEnum roleEnum : RolesEnum.values()) {
            if(roleEnum.getId().equals(id)) {
                return roleEnum.role;
            }
        }
        return null;
    }

    public static String getRoleValueByRole(String role) {
        for (RolesEnum roleEnum : RolesEnum.values()) {
            if(roleEnum.getRole().equals(role)) {
                return roleEnum.value;
            }
        }
        return null;
    }
}
