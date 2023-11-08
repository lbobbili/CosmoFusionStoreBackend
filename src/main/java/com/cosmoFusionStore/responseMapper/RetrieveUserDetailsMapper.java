package com.cosmoFusionStore.responseMapper;

import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.rest.responseModel.UserDetailsResponse;
import org.springframework.stereotype.Component;

@Component
public class RetrieveUserDetailsMapper {

    public UserDetailsResponse mapUserDetails(User user) {
        return UserDetailsResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .registrationStatus(user.getRegistrationStatus())
                .build();
    }
}
