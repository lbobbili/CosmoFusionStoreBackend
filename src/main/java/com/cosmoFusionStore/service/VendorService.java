package com.cosmoFusionStore.service;

import com.cosmoFusionStore.acquire.UserAcquirer;
import com.cosmoFusionStore.acquire.VendorAcquirer;
import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.Role;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.Vendor;
import com.cosmoFusionStore.rest.requestModel.AuthenticateRequest;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.requestModel.VendorRegistrationRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.rest.responseModel.UserResponse;
import com.cosmoFusionStore.rest.responseModel.VendorResponse;
import com.cosmoFusionStore.serviceexception.SignupDataAcquirerException;
import com.cosmoFusionStore.validate.RegisterRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendorService {

    @Autowired
    private RegisterRequestValidation requestValidation;

    @Autowired
    private VendorAcquirer vendorAcquirer;

    public VendorDTO register(VendorRegistrationRequest vendorRegistrationRequest) {
        this.requestValidation.validateVendorRegisterRequest(vendorRegistrationRequest);
        boolean isExistingUser = this.vendorAcquirer.hasEmailAddress(vendorRegistrationRequest.getEmail());
        VendorDTO vendorDTO;
        if (!isExistingUser) {
            vendorDTO = this.vendorAcquirer.signupData(vendorRegistrationRequest);
            vendorDTO.setStatus("sucess");
        } else
            throw new SignupDataAcquirerException("Can't able to signup as vendor is already present with this email");

        return vendorDTO;
    }

    public Vendor signin(AuthenticateRequest request) {
        Vendor vendor = this.vendorAcquirer.retrieveUserData(request.getEmail(), request.getPassword());
        if(vendor != null) vendor.setPassword("***Masked***");
        else {
            vendor = new Vendor();
            vendor.setEmail("Not Found");
            vendor.setPassword("Not Found");
        }
        return vendor;
    }

    public List<Vendor> retrievePendingRegistrations(String status) {
        this.requestValidation.validatePathParams("status", status);
        return this.vendorAcquirer.retrieveRegistrations(status);
    }

    public CommonResponse updateRegistrationStatus(UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        this.requestValidation.validateUpdateRegistrationRequest(updateRegistrationStatusRequest);
        this.vendorAcquirer.updateRegistration(updateRegistrationStatusRequest);
        return CommonResponse.builder().status("SUCCESS").statusText("Successfully updated Registration Status.").build();
    }
}
