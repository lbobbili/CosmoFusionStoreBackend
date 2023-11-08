package com.cosmoFusionStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name="role_id")
    private String roleId;

    @Column(name="role_name")
    private String roleName;

    @Column(name="user_id")
    private String userId;
}
