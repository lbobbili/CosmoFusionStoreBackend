package com.cosmoFusionStore.dao;

import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;

import java.util.List;

public interface UserDAO {
    void save(User user);
    User retrieveUserByCredentials(String email, String password);
    User retrieveUserByEmail(String email);
    List<UserWithRoles> retrieveRegistrations(String status);
    int updateUserRegistration(String userId, String registrationStatus);
}
