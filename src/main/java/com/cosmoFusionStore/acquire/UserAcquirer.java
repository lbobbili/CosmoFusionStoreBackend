package com.cosmoFusionStore.acquire;

import com.cosmoFusionStore.dao.RoleDAO;
import com.cosmoFusionStore.dao.UserDAO;
import com.cosmoFusionStore.dto.UserDTO;
import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.Role;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.enums.RegistrationStatusEnum;
import com.cosmoFusionStore.rest.requestModel.RegistrationRequest;
import com.cosmoFusionStore.rest.requestModel.UpdateRegistrationStatusRequest;
import com.cosmoFusionStore.serviceexception.BadRequestException;
import com.cosmoFusionStore.serviceexception.DatabasePersistenceException;
import com.cosmoFusionStore.serviceexception.SignupDataAcquirerException;
import com.cosmoFusionStore.util.CosmoFusionStoreUtil;
import com.cosmoFusionStore.enums.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAcquirer {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    private static final String UPDATE_STATUS = "SUCCESS";

    public UserDTO signupData(RegistrationRequest registrationRequest) {
        String userId = CosmoFusionStoreUtil.generateUUID();
        User user = User.builder()
                .userId(userId)
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .build();


//        if("3".equals(registrationRequest.getRegisterType())) {
//            user.setRegistrationStatus(RegistrationStatusEnum.APPROVED.getRegistrationType());
//        } else {
//            user.setRegistrationStatus(RegistrationStatusEnum.PENDING.getRegistrationType());
//        }

        try {
            this.userDAO.save(user);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("Exception occurred at the time of inserting user data.");
        }

        return UserDTO.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();


      //  String roleId = CosmoFusionStoreUtil.generateUUID();
     //   String userRole = RolesEnum.getRoleById(registrationRequest.getRegisterType());
//        if(userRole != null) {
//            Role role = Role.builder()
//                    .roleId(roleId)
//                    .roleName(userRole)
//                    .userId(userId)
//                    .build();
//
//            try {
//                this.roleDAO.save(role);
//            } catch(Exception exception) {
//                throw new SignupDataAcquirerException("Exception occurred at the time of inserting user role data.");
//            }
//            return UserDTO.builder()
//                    .email(user.getEmail())
//                    .firstName(user.getFirstName())
//                    .lastName(user.getLastName())
//                    .role(role.getRoleName())
//                    .build();
//        }
//        throw new BadRequestException("RegisterType is invalid.");
    }

    public boolean hasEmailAddress(String email) {
        boolean isExistingEmail = false;
        try {
            User user = this.userDAO.retrieveUserByEmail(email);
            isExistingEmail = hasUser(user);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("User with this email id is already present.");
        }
        return isExistingEmail;
    }

    public User retrieveUserData(String email, String password) {
        try {
            return this.userDAO.retrieveUserByCredentials(email, password);
        } catch(Exception exception) {
            throw new SignupDataAcquirerException("User with this email id is already present.");
        }
    }

    public List<Role> retrieveRoleByUserId(String userId) {
        return this.roleDAO.retrieveRoleByUserId(userId);
    }

    public User retrieveUser(String email) {
        User user = this.userDAO.retrieveUserByEmail(email);
        if(hasUser(user)) {
            return user;
        }
        return null;
    }

    private boolean hasUser(User user) {
        return (user != null && user.getUserId() != null && !user.getUserId().isBlank());
    }

    public List<UserWithRoles> retrieveRegistrations(String status) {
        String registrationType = RegistrationStatusEnum.getRegistrationTypeByValue(status.toLowerCase());
        List<UserWithRoles> userDetails = this.userDAO.retrieveRegistrations(registrationType);
        for(UserWithRoles user: userDetails) {
            user.setRoleName(RolesEnum.getRoleValueByRole(user.getRoleName()));
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
        int rowsAffected = this.userDAO.updateUserRegistration(updateRegistrationStatusRequest.getUserId(), updateRegistrationStatusRequest.getRegistrationStatus());
        if(rowsAffected > 1 || rowsAffected <= 0) {
            throw new DatabasePersistenceException("Something wrong with Updating the Registration Status.");
        }
    }
}
