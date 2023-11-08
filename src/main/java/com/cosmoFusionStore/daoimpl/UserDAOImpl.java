package com.cosmoFusionStore.daoimpl;

import com.cosmoFusionStore.dao.UserDAO;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String RETRIEVE_USER_BY_CREDENTIALS_QUERY = "FROM User WHERE email=:userEmail AND password=:userPassword";
    private static final String RETRIEVE_USER_BY_EMAIL_QUERY = "FROM User WHERE email=:userEmail";

    private static final String RETRIEVE_PENDING_REGISTRATIONS_WITH_USER_ROLE = "SELECT u.userId, u.firstName, u.lastName, u.email, u.phoneNumber, u.registrationStatus, r.roleName FROM User u JOIN Role r ON u.userId = r.userId WHERE u.registrationStatus =: registrationStatus";
    private static final String UPDATE_REGISTRATION_STATUS = "UPDATE User u SET u.registrationStatus=:registrationStatus WHERE u.userId=:userId";

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User retrieveUserByCredentials(String email, String password) {
        TypedQuery<User> query = entityManager.createQuery(RETRIEVE_USER_BY_CREDENTIALS_QUERY, User.class);
        query.setParameter("userEmail", email);
        query.setParameter("userPassword", password);

        List<User> userList = query.getResultList();
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User retrieveUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(RETRIEVE_USER_BY_EMAIL_QUERY, User.class);
        query.setParameter("userEmail", email);

        List<User> userList = query.getResultList();
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public List<UserWithRoles> retrieveRegistrations(String status) {
        TypedQuery<Object[]> query = entityManager.createQuery(RETRIEVE_PENDING_REGISTRATIONS_WITH_USER_ROLE, Object[].class);
        query.setParameter("registrationStatus", status);

        List<UserWithRoles> pendingUsers = new ArrayList<>();
        for(Object[] object: query.getResultList()) {
            UserWithRoles pendingUser = UserWithRoles.builder()
                    .userId((String) object[0])
                    .firstName((String) object[1])
                    .lastName((String) object[2])
                    .email((String) object[3])
                    .phoneNumber((String) object[4])
                    .registrationStatus((String) object[5])
                    .roleName((String) object[6])
                    .build();

            pendingUsers.add(pendingUser);
        }
        return pendingUsers;
    }

    @Override
    @Transactional
    public int updateUserRegistration(String userId, String registrationStatus) {
        Query query = entityManager.createQuery(UPDATE_REGISTRATION_STATUS);
        query.setParameter("registrationStatus", registrationStatus);
        query.setParameter("userId", userId);

        return query.executeUpdate();
    }
}
