package com.project.dudu.repositories;

import com.project.dudu.entities.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {

    // 이름으로 관리자 검색
    Optional<ManagerEntity> findByName(String name);

    // 이메일로 관리자 검색 (추가된 메서드)
    Optional<ManagerEntity> findByEmail(String email);

    // 이메일 존재 여부 확인 (추가된 메서드)
    boolean existsByEmail(String email);
}