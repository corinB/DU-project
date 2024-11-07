package com.project.dudu.repositories;

import com.project.dudu.entities.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    Optional<ManagerEntity> findByName(String name);
}
