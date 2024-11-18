package com.project.dudu.repositories;

import com.project.dudu.entities.LostItemReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LostItemReportRepository extends JpaRepository<LostItemReportEntity, Long> {

    // 수정된 메서드: itemName 을 기준으로 검색
    List<LostItemReportEntity> findByItemNameContaining(String itemName);

    // 특정 이름의 신고를 모두 찾는 메서드
    List<LostItemReportEntity> findByReporterName(String reporterName);

    // 특정 위치에서 발생한 신고를 모두 찾는 메서드
    List<LostItemReportEntity> findByLocation(String foundLocation);

    // 최근 N일 이내에 신고된 항목 조회
    List<LostItemReportEntity> findByReportDateAfter(LocalDateTime date);

    // 신고 제목으로 신고를 검색 (부분 일치)
    List<LostItemReportEntity> findByTitleContaining(String keyword);
}