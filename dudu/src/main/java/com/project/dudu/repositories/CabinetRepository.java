package com.project.dudu.repositories;

import com.project.dudu.entities.CabinetEntity;
import com.project.dudu.enums.Colleges;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinetRepository extends JpaRepository<CabinetEntity, Long> {
    Page<CabinetEntity> findAll(Pageable page);
    List<CabinetEntity> findAllByDepartment(Colleges department);
}
