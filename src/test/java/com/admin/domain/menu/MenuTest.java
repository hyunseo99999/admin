package com.admin.domain.menu;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MenuTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    @Rollback(value = false)
    void save() {
        Menu menu = Menu.builder()
                .menuNm("사용자관리")
                .sort(1L)
                .build();
        em.persist(menu);


        Menu sub1 = Menu.builder()
                        .menuNm("사용자관리-1")
                        .sort(1L)
                        .parent(menu)
                        .build();
        em.persist(sub1);

        Menu sub2 = Menu.builder()
                        .menuNm("사용자관리-2")
                        .sort(2L)
                        .parent(menu)
                        .build();
        em.persist(sub2);

    }
}