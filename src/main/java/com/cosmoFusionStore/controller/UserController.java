package com.cosmoFusionStore.controller;

import com.cosmoFusionStore.dto.UserDTO;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.responseMapper.RetrieveUserDetailsMapper;
import com.cosmoFusionStore.rest.requestModel.AuthenticateRequest;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.rest.responseModel.UserDetailsResponse;
import com.cosmoFusionStore.rest.responseModel.UserResponse;
import com.cosmoFusionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RetrieveUserDetailsMapper retrieveUserDetailsMapper;

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody RegistrationRequest registrationRequest) {
        UserResponse userResponse = this.userService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/authenticate")
    public UserResponse authenticateUser(@RequestBody AuthenticateRequest authenticateRequest) {
        return this.userService.signin(authenticateRequest);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDetailsResponse> retrieveUserDetails(@PathVariable String email) {
        User user = this.userService.retrieveUserDetails(email);
        UserDetailsResponse userDetailsResponse = this.retrieveUserDetailsMapper.mapUserDetails(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsResponse);
    }

    @GetMapping("/registrations/{status}")
    public ResponseEntity<List<UserWithRoles>> retrievePendingRegistrations(@PathVariable String status) {
        List<UserWithRoles> userDetails = this.userService.retrievePendingRegistrations(status);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @PutMapping("/registration/status")
    public ResponseEntity<CommonResponse> updateRegistrationStatus(@RequestBody UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        CommonResponse commonResponse = this.userService.updateRegistrationStatus(updateRegistrationStatusRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponse);
    }

}
