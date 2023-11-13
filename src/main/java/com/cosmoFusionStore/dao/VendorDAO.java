package com.cosmoFusionStore.dao;

import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.Vendor;

import java.util.List;

public interface VendorDAO {

    Vendor retrieveUserByEmail(String email);

    void save(Vendor vendor);

    Vendor retrieveUserByCredentials(String email, String password);

    List<Vendor> retrieveRegistrations(String status);

    int updateUserRegistration(String userId, String registrationStatus);

    List<Vendor> getVendorId(int vendorId);
}
