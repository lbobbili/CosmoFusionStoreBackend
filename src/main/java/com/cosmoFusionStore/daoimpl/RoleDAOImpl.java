package com.cosmoFusionStore.daoimpl;

import com.cosmoFusionStore.dao.RoleDAO;
import com.cosmoFusionStore.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private static final String RETRIEVE_ROLE_BY_USER_ID_QUERY = "FROM Role WHERE userId=:userIdFromDb";

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Role role) {
        this.entityManager.persist(role);
    }

    @Override
    public List<Role> retrieveRoleByUserId(String userId) {
        TypedQuery<Role> query = entityManager.createQuery(RETRIEVE_ROLE_BY_USER_ID_QUERY, Role.class);
        query.setParameter("userIdFromDb", userId);

        List<Role> roleList = query.getResultList();
        return roleList;

    }
}
