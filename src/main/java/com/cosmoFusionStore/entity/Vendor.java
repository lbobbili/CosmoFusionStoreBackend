package com.cosmoFusionStore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="vendor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vendor_id")
    private Integer vendorId;

    @Column(name="vendor_company")
    private String vendorCompany;

    @Column(name="vendor_name")
    private String vendorName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="brand_name")
    private String brandName;

    @Column(name="details_about_vendor")
    private String detailsAboutVendor;

    @Column(name="status")
    private String status;
}
