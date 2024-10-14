package com.project.dudu.Repository;

import com.project.dudu.Entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    Optional<ManagerEntity> findByName(String name);
}
