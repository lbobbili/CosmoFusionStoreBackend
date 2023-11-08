package com.cosmoFusionStore.dao;

import com.cosmoFusionStore.entity.Role;

import java.util.List;

public interface RoleDAO {
    void save(Role role);
    List<Role> retrieveRoleByUserId(String userId);
}
