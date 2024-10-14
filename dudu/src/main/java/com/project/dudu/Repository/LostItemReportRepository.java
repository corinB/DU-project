package com.project.dudu.Repository;

import com.project.dudu.Entity.LostItemReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostItemReportRepository extends JpaRepository<LostItemReportEntity, Long> {
}