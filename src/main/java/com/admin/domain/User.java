package com.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter  @Setter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 1, nullable = false)
    private String useYn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoleMapping> roleMappings = new ArrayList<>();

    public static User createUser(User user, RoleMapping ...roleMappings) {
        for (RoleMapping roleMapping : roleMappings) {
            user.addRoleMapping(roleMapping);
        }
        return user;
    }

    public void addRoleMapping(RoleMapping roleMapping) {
        if (roleMappings.isEmpty()) {
            roleMappings = new ArrayList<>();
            roleMappings.add(roleMapping);
        } else {
            roleMappings.add(roleMapping);
        }

        roleMapping.setUser(this);
    }

    @Builder
    public User(Long id, String username, String password, String useYn, List<RoleMapping> roleMappings) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.useYn = useYn;
        this.roleMappings = roleMappings;
    }
}
