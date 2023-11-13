package com.cosmoFusionStore.controller;

import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.Vendor;
import com.cosmoFusionStore.rest.requestModel.AuthenticateRequest;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.requestModel.VendorRegistrationRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.rest.responseModel.UserResponse;
import com.cosmoFusionStore.rest.responseModel.VendorResponse;
import com.cosmoFusionStore.service.UserService;
import com.cosmoFusionStore.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
@CrossOrigin(origins = "http://localhost:4200")
public class VendorController {
    @Autowired
    private VendorService vendorService;



    @PostMapping("/registration")
    public ResponseEntity<VendorDTO> registration(@RequestBody VendorRegistrationRequest vendorRegistrationRequest) {
        VendorDTO vendorDTO  = this.vendorService.register(vendorRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorDTO);
    }

    @PostMapping("/authenticate")
    public Vendor authenticateUser(@RequestBody AuthenticateRequest authenticateRequest) {
        return this.vendorService.signin(authenticateRequest);
    }

    @GetMapping("/registrations/{status}")
    public ResponseEntity<List<Vendor>> retrievePendingRegistrations(@PathVariable String status) {
        List<Vendor> userDetails = this.vendorService.retrievePendingRegistrations(status);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @PutMapping("/registration/status")
    public ResponseEntity<CommonResponse> updateRegistrationStatus(@RequestBody UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        CommonResponse commonResponse = this.vendorService.updateRegistrationStatus(updateRegistrationStatusRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponse);
    }

}
