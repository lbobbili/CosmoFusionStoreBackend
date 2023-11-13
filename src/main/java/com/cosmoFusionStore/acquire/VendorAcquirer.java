package com.cosmoFusionStore.acquire;

import com.cosmoFusionStore.dao.RoleDAO;
import com.cosmoFusionStore.dao.UserDAO;
import com.cosmoFusionStore.dao.VendorDAO;
import com.cosmoFusionStore.dto.UserDTO;
import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.*;
import com.cosmoFusionStore.enums.RegistrationStatusEnum;
import com.cosmoFusionStore.enums.RolesEnum;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.requestModel.VendorRegistrationRequest;
import com.cosmoFusionStore.serviceexception.BadRequestException;
import com.cosmoFusionStore.serviceexception.DatabasePersistenceException;
import com.cosmoFusionStore.serviceexception.SignupDataAcquirerException;
import com.cosmoFusionStore.util.CosmoFusionStoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendorAcquirer {

    @Autowired
    private VendorDAO vendorDAO;

    @Autowired
    private RoleDAO roleDAO;

    public boolean hasEmailAddress(String email) {
        boolean isExistingEmail = false;
        try {
            Vendor vendor = this.vendorDAO.retrieveUserByEmail(email);
            isExistingEmail = hasUser(vendor);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("Vendor with this email id is already present.");
        }
        return isExistingEmail;
    }

    private boolean hasUser(Vendor vendor) {
        return (vendor != null && vendor.getVendorId() != 0);
    }

    public VendorDTO signupData(VendorRegistrationRequest vendorregistrationRequest) {
        String vendorId = CosmoFusionStoreUtil.generateUUID();
        Vendor vendor = Vendor.builder()
                .vendorCompany(vendorregistrationRequest.getVendorCompany())
                .vendorName(vendorregistrationRequest.getVendorName())
                .email(vendorregistrationRequest.getEmail())
                .password(vendorregistrationRequest.getPassword())
                .phoneNumber(vendorregistrationRequest.getPhoneNumber())
                .brandName(vendorregistrationRequest.getBrandName())
                .detailsAboutVendor(vendorregistrationRequest.getDetailsAboutVendor())
                .status(vendorregistrationRequest.getStatus())
                .build();

        try {
            this.vendorDAO.save(vendor);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("Exception occurred at the time of inserting user data.");
        }
            return VendorDTO.builder()
                    .email(vendor.getEmail())
                    .vendorName(vendor.getVendorName())
                    .vendorCompany(vendor.getVendorCompany())
                    .brandName(vendor.getBrandName())
                    .build();
    }

    public Vendor retrieveUserData(String email, String password) {
        try {
            return this.vendorDAO.retrieveUserByCredentials(email, password);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("Vendor with this email id is already present.");
        }
    }

    public List<Role> retrieveRoleByUserId(String userId) {
        return this.roleDAO.retrieveRoleByUserId(userId);
    }


    public List<Vendor> retrieveRegistrations(String status) {
        String registrationType = RegistrationStatusEnum.getRegistrationTypeByValue(status.toLowerCase());
        List<Vendor> userDetails = this.vendorDAO.retrieveRegistrations(registrationType);
        for(Vendor user: userDetails) {
          //  user.setRoleName(RolesEnum.getRoleValueByRole(user.getRoleName()));
        }
        return userDetails;
    }

    public void updateRegistration(UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        for(RegistrationStatusEnum registrationStatusEnum: RegistrationStatusEnum.values()) {
            if(registrationStatusEnum.getStatus().equals(updateRegistrationStatusRequest.getRegistrationStatus())) {
                updateRegistrationStatusRequest.setRegistrationStatus(registrationStatusEnum.getRegistrationType());
                break;
            }
        }
        int rowsAffected = this.vendorDAO.updateUserRegistration(updateRegistrationStatusRequest.getUserId(), updateRegistrationStatusRequest.getRegistrationStatus());
        if(rowsAffected > 1 || rowsAffected <= 0) {
            throw new DatabasePersistenceException("Something wrong with Updating the Registration Status.");
        }
    }

    public List<Vendor> getVendor(int vendorId){
        return this.vendorDAO.getVendorId(vendorId);
    }
}
