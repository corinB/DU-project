package com.project.dudu.repositories;

import com.project.dudu.entities.CabinetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetRepository extends JpaRepository<CabinetEntity, Long> {
}
