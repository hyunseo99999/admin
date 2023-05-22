package com.admin.domain.user;

import com.admin.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_role_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoleGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_code", nullable = false, length = 20)
    private String roleCode;

    @Column(name = "role_nm", nullable = false, length = 100)
    private String roleNm;

    @Column(name = "role_dc", nullable = true, length = 4000)
    private String roleDc;

    @OneToMany(mappedBy = "roleGroup", fetch = FetchType.LAZY)
    private List<RoleMapping> roleMappings = new ArrayList<>();

    @Builder
    public RoleGroup(Long id, String roleNm, String roleDc, String roleCode, List<RoleMapping> roleMappings) {
        this.id = id;
        this.roleNm = roleNm;
        this.roleDc = roleDc;
        this.roleMappings = roleMappings;
        this.roleCode = roleCode;
    }
}
