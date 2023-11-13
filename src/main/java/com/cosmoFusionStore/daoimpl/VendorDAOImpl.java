package com.cosmoFusionStore.daoimpl;

import com.cosmoFusionStore.dao.VendorDAO;
import com.cosmoFusionStore.dto.VendorDTO;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.entity.User;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.Vendor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VendorDAOImpl implements VendorDAO {

    private static final String RETRIEVE_USER_BY_EMAIL_QUERY = "FROM Vendor WHERE email=:userEmail";
    private static final String RETRIEVE_USER_BY_CREDENTIALS_QUERY = "FROM Vendor WHERE email=:userEmail AND password=:userPassword";

    private static final String RETRIEVE_PENDING_REGISTRATIONS_WITH_USER_ROLE = "FROM Vendor WHERE status =: status";

    private static final String UPDATE_REGISTRATION_STATUS = "UPDATE Vendor v SET v.status=:status WHERE v.vendorId=:userId";

    private static  final String RETRIEVE_VENDOR_NAME_BY_ID = "FROM Vendor WHERE vendorId =: vendorId";

    @Autowired
    private EntityManager entityManager;


    @Override
    public Vendor retrieveUserByCredentials(String email, String password) {
        TypedQuery<Vendor> query = entityManager.createQuery(RETRIEVE_USER_BY_CREDENTIALS_QUERY, Vendor.class);
        query.setParameter("userEmail", email);
        query.setParameter("userPassword", password);

        List<Vendor> userList = query.getResultList();
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public Vendor retrieveUserByEmail(String email) {
        TypedQuery<Vendor> query = entityManager.createQuery(RETRIEVE_USER_BY_EMAIL_QUERY, Vendor.class);
        query.setParameter("userEmail", email);

        List<Vendor> userList = query.getResultList();
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Transactional
    public void save(Vendor vendor) {
        entityManager.persist(vendor);
    }

    @Override
    public List<Vendor> retrieveRegistrations(String status) {
        TypedQuery<Vendor> query = entityManager.createQuery(RETRIEVE_PENDING_REGISTRATIONS_WITH_USER_ROLE, Vendor.class);
        query.setParameter("status", status);
        List<Vendor> vendorList = query.getResultList();
        return vendorList;
    }

    @Override
    @Transactional
    public int updateUserRegistration(String userId, String registrationStatus) {
        Query query = entityManager.createQuery(UPDATE_REGISTRATION_STATUS);
        query.setParameter("status", registrationStatus);
        query.setParameter("userId", userId);

        return query.executeUpdate();
    }

    @Override
    public List<Vendor> getVendorId(int vendorId) {
        TypedQuery<Vendor> query = entityManager.createQuery(RETRIEVE_VENDOR_NAME_BY_ID, Vendor.class);
        query.setParameter("vendorId", vendorId);
        List<Vendor> vendorDetails = query.getResultList();
        return vendorDetails;
    }
}
