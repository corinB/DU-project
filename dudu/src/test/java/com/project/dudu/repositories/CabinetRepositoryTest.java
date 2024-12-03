package com.project.dudu.repositories;

import com.project.dudu.entities.CabinetEntity;
import com.project.dudu.enums.Colleges;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CabinetRepositoryTest {
    @Autowired
    CabinetRepository cabinetRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void addCabinet() {
        for (long i = 0; i < 10; i++) {
            var cabinet = CabinetEntity.builder().cabinetId(i).department(Colleges.Engineering).build();
            cabinetRepository.save(cabinet);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void save220Cabinet() {
        Colleges [] colleges = Colleges.values();
        for (Colleges college : colleges) {
            for (long i = 0; i < 20; i++) {
                var cabinet = CabinetEntity.builder().department(college).build();
                cabinetRepository.save(cabinet);
            }
            }
        }

}
