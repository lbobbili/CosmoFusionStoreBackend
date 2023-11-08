package com.cosmoFusionStore.service;

import com.cosmoFusionStore.acquire.UserAcquirer;
import com.cosmoFusionStore.dto.UserDTO;
import com.cosmoFusionStore.entity.Role;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.rest.requestModel.AuthenticateRequest;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.rest.responseModel.CommonResponse;
import com.cosmoFusionStore.rest.responseModel.RolesResponse;
import com.cosmoFusionStore.rest.responseModel.UserResponse;
import com.cosmoFusionStore.serviceexception.SignupDataAcquirerException;
import com.cosmoFusionStore.enums.RolesEnum;
import com.cosmoFusionStore.validate.RegisterRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    private RegisterRequestValidation requestValidation;

    @Autowired
    private UserAcquirer userAcquirer;

    public UserResponse register(RegistrationRequest registrationRequest) {
        this.requestValidation.validateRegisterRequest(registrationRequest);
        boolean isExistingUser = this.userAcquirer.hasEmailAddress(registrationRequest.getEmail());
        if(!isExistingUser) {
            this.userAcquirer.signupData(registrationRequest);
        }
        else throw new SignupDataAcquirerException("Can't able to signup as user is already present with this email");

        return signin(new AuthenticateRequest(registrationRequest.getEmail(), registrationRequest.getPassword()));
    }

    public UserResponse signin(AuthenticateRequest request) {
        UserResponse userResponse = new UserResponse();
        User user = this.userAcquirer.retrieveUserData(request.getEmail(), request.getPassword());
        if(user != null) user.setPassword("***Masked***");
        else {
            user = new User();
            user.setEmail("Not Found");
            user.setPassword("Not Found");
        }
        List<Role> roleList = this.userAcquirer.retrieveRoleByUserId(user.getUserId());
        List<String> roleNames = roleList.stream().map(Role::getRoleName).toList();
        if(roleNames.size() > 0) {
           userResponse = UserResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .roles(roleNames)
                    .userId(user.getUserId())
                    .build();
        }
        return userResponse;
    }

    public List<RolesResponse> retrieveUserRoles() {
        List<RolesResponse> rolesResponseList = new ArrayList<>();
        RolesEnum[] roleEnums = RolesEnum.values();
        for(int i = 0; i < roleEnums.length; i++) {
            RolesEnum roleEnum = roleEnums[i];
            String roleValue = roleEnum.getValue();
            String roleId = roleEnum.getId();
            RolesResponse rolesResponse = new RolesResponse(roleId, roleValue);
            rolesResponseList.add(rolesResponse);
        }
        return rolesResponseList;
    }

    public User retrieveUserDetails(String email) {
        this.requestValidation.validatePathParams("email", email);
        User user = this.userAcquirer.retrieveUser(email);
        if(user != null) return user;
        else user = User.builder().email("Not Found").password("Not Found").build();
        return user;
    }

    public List<UserWithRoles> retrievePendingRegistrations(String status) {
        this.requestValidation.validatePathParams("status", status);
        return this.userAcquirer.retrieveRegistrations(status);
    }

    public CommonResponse updateRegistrationStatus(UpdateRegistrationStatusRequest updateRegistrationStatusRequest) {
        this.requestValidation.validateUpdateRegistrationRequest(updateRegistrationStatusRequest);
        this.userAcquirer.updateRegistration(updateRegistrationStatusRequest);
        return CommonResponse.builder().status("SUCCESS").statusText("Successfully updated Registration Status.").build();
    }
}
