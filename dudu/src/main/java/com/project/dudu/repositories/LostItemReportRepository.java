package com.project.dudu.repositories;

import com.project.dudu.entities.LostItemReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostItemReportRepository extends JpaRepository<LostItemReportEntity, Long> {
}