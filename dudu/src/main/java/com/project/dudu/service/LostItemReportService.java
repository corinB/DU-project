package com.project.dudu.service;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.entities.LostItemReportEntity;
import com.project.dudu.repositories.LostItemReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostItemReportService {

    private final LostItemReportRepository lostItemReportRepository;

    // 모든 분실물 신고 조회
    public List<LostItemReportDto> getAllReports() {
        return lostItemReportRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ID로 분실물 신고 조회
    public Optional<LostItemReportDto> getReportById(Long id) {
        return lostItemReportRepository.findById(id).map(this::convertToDto);
    }

    // 새로운 분실물 신고 생성
    public void createReport(LostItemReportDto lostItemReportDto) {
        LostItemReportEntity entity = convertToEntity(lostItemReportDto);
        lostItemReportRepository.save(entity);
    }

    // 분실물 신고 삭제
    public void deleteReport(Long id) {
        lostItemReportRepository.deleteById(id);
    }

    // 분실물 상태 변경
    public void updateReportStatus(Long id, String newStatus) {
        LostItemReportEntity report = lostItemReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "의 신고를 찾을 수 없습니다."));
        report.setStatus(newStatus);
        report.setUpdateAt(LocalDateTime.now());
        lostItemReportRepository.save(report);
    }

    // 이름으로 신고 조회
    public List<LostItemReportDto> searchByItemName(String itemName) {
        return lostItemReportRepository.findByItemNameContaining(itemName).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 신고자 이름으로 신고 조회
    public List<LostItemReportDto> searchByReporterName(String reporterName) {
        return lostItemReportRepository.findByReporterName(reporterName).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 위치로 신고 조회
    public List<LostItemReportDto> searchByLocation(String foundLocation) {
        return lostItemReportRepository.findByFoundLocation(foundLocation).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 카테고리로 신고 조회
    public List<LostItemReportDto> searchByCategory(String category) {
        return lostItemReportRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 최근 신고 조회
    public List<LostItemReportDto> searchRecentReports(int days) {
        LocalDateTime date = LocalDateTime.now().minusDays(days);
        return lostItemReportRepository.findByCreateAtAfter(date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 상태별 분실물 목록 조회 메서드
    public List<LostItemReportDto> searchByStatus(String status) {
        return lostItemReportRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Entity -> DTO 변환
    private LostItemReportDto convertToDto(LostItemReportEntity entity) {
        return new LostItemReportDto(
                entity.getReportId(),
                entity.getItemName(),
                entity.getCategory(),
                entity.getFoundLocation(),
                entity.getReporterName(),
                entity.getFoundTime(),
                entity.getStatus()
        );
    }

    // DTO -> Entity 변환
    private LostItemReportEntity convertToEntity(LostItemReportDto dto) {
        return LostItemReportEntity.builder()
                .reportId(dto.getReportId())
                .itemName(dto.getItemName())
                .category(dto.getCategory())
                .foundTime(dto.getFoundTime())
                .foundLocation(dto.getFoundLocation())
                .reporterName(dto.getReporterName())
                .status(dto.getStatus() == null ? "보관중" : dto.getStatus())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    // ID 존재 여부 확인
    public boolean existsById(Long id) {
        return lostItemReportRepository.existsById(id);
    }
}
