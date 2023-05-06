package com.admin.domain.menu;

import com.admin.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    private Menu parent;

    @Column(name = "menu_nn", nullable = false, length = 100)
    private String menuNm;

    @Column(name = "sort", nullable = false, length = 100)
    private Long sort;

    @OneToMany(mappedBy = "parent")
    private List<Menu> child = new ArrayList<>();

       //==연관관계 메서드==//
    public void addChildMenu(Menu menu) {
        this.child.add(menu);
        menu.setParent(this);
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Builder
    public Menu(Long id, Menu parent, String menuNm, Long sort, List<Menu> child) {
        this.id = id;
        this.parent = parent;
        this.menuNm = menuNm;
        this.sort = sort;
        this.child = child;
    }
}
