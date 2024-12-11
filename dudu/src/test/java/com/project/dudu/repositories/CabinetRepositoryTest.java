//package com.project.dudu.repositories;
//
//import com.project.dudu.entities.CabinetEntity;
//import com.project.dudu.enums.Colleges;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CabinetRepositoryTest {
//    @Autowired
//    CabinetRepository cabinetRepository;
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void addCabinet() {
//        for (int i = 0; i < 10; i++) {
//            var cabinet = CabinetEntity.builder()
//                    // .cabinetId(i) // 제거: 자동 생성되도록 설정
//                    .department(Colleges.Engineering)
//                    .createAt(LocalDateTime.now()) // 필요한 경우 추가
//                    .updateAt(LocalDateTime.now()) // 필요한 경우 추가
//                    .build();
//            cabinetRepository.save(cabinet);
//        }
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void save220Cabinet() {
//        Colleges[] colleges = Colleges.values();
//        for (Colleges college : colleges) {
//            for (int i = 0; i < 20; i++) {
//                var cabinet = CabinetEntity.builder()
//                        // .cabinetId(i) // 제거: 자동 생성되도록 설정
//                        .department(college)
//                        .createAt(LocalDateTime.now()) // 필요한 경우 추가
//                        .updateAt(LocalDateTime.now()) // 필요한 경우 추가
//                        .build();
//                cabinetRepository.save(cabinet);
//            }
//        }
//    }
//}

package com.project.dudu.repositories;

import com.project.dudu.entities.CabinetEntity;
import com.project.dudu.enums.Colleges;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CabinetRepositoryTest {

    @Autowired
    private CabinetRepository cabinetRepository;

    @Test
    void insertCabinets() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            CabinetEntity cabinet = CabinetEntity.builder()
                    .department(Colleges.values()[i % Colleges.values().length])  // 부서 순환 입력
                    .createAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .build();

            cabinetRepository.save(cabinet);
        });

        // 저장 확인
        long count = cabinetRepository.count();
        assertThat(count).isGreaterThanOrEqualTo(30);
    }
}