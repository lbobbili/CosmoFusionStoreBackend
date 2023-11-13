package com.cosmoFusionStore.daoimpl;

import com.cosmoFusionStore.dao.ProductDAO;
import com.cosmoFusionStore.entity.Product;
import com.cosmoFusionStore.entity.UserWithRoles;
import com.cosmoFusionStore.entity.Vendor;
import com.cosmoFusionStore.rest.requestModel.AddProductsToCartRequest;
import com.cosmoFusionStore.rest.requestModel.ProductRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private static final String RETRIEVE_PRODUCTS_BY_VENDOR = "FROM Product WHERE userId =: vendorId";
    private static final String RETRIEVE_PRODUCTS_BY_STATUS = "FROM Product WHERE registrationStatus =: status";
    private static final String RETRIEVE_PRODUCTS_BY_ID = "FROM Product WHERE productId =: id";
    private static final String UPDATE_PRODUCT_STATUS = "UPDATE Product SET registrationStatus=:status WHERE productId=:id";


    @Autowired
    private EntityManager entityManager;
    @Override
    @Transactional
    public void addProduct(Product product) {
        this.entityManager.persist(product);
    }

    public List<Product> retrieveProductsByVendorId(String vendorId) {
        TypedQuery<Product> query = entityManager.createQuery(RETRIEVE_PRODUCTS_BY_VENDOR, Product.class);
        query.setParameter("vendorId", vendorId);
        List<Product> productList = query.getResultList();
        return productList;
    }

    @Override
    public List<Product> retrieveProductsByStatus(String status) {
        TypedQuery<Product> query = entityManager.createQuery(RETRIEVE_PRODUCTS_BY_STATUS, Product.class);
        query.setParameter("status", status);
        List<Product> productList = query.getResultList();
        return productList;
    }

    @Override
    @Transactional
    public int updateProductStatus(ProductRequest productRequest) {
        Query query = entityManager.createQuery(UPDATE_PRODUCT_STATUS);
        query.setParameter("status", productRequest.getStatus());
        query.setParameter("id", productRequest.getProductId());

        return query.executeUpdate();
    }

    @Override
    public Product retrieveProductById(int productId) {
        TypedQuery<Product> query = entityManager.createQuery(RETRIEVE_PRODUCTS_BY_ID, Product.class);
        query.setParameter("id", productId);
        List<Product> productList = query.getResultList();
        return productList.get(0);
    }


}
