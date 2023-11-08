package com.cosmoFusionStore.util;

import com.cosmoFusionStore.enums.RolesEnum;
import com.cosmoFusionStore.serviceexception.BadRequestException;

import java.util.UUID;

public class CosmoFusionStoreUtil {

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String mapRolesIdWithRoles(String role) {
        String mappedRoleId = null;
        switch (role) {
            case CosmoFusionStoreConstants.ADMIN:
                mappedRoleId = RolesEnum.ROLE_ADMIN.getId();
                break;
            case CosmoFusionStoreConstants.VENDOR:
                mappedRoleId = RolesEnum.ROLE_VENDOR.getId();
                break;
            case CosmoFusionStoreConstants.CUSTOMER:
                mappedRoleId = RolesEnum.ROLE_CUSTOMER.getId();
                break;
        }
        return mappedRoleId;
    }

    public static void notNullNorEmptyErrorMsg(String msg) {
        throw new BadRequestException(msg + " should not be null or empty");
    }
}
