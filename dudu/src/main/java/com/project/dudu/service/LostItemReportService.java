package com.project.dudu.service;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.entities.LostItemReportEntity;
import com.project.dudu.repositories.LostItemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LostItemReportService {

    private final LostItemReportRepository lostItemReportRepository;

    @Autowired
    public LostItemReportService(LostItemReportRepository lostItemReportRepository) {
        this.lostItemReportRepository = lostItemReportRepository;
    }

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
        return lostItemReportRepository.findByLocation(foundLocation).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 최근 신고 조회
    public List<LostItemReportDto> searchRecentReports(int days) {
        LocalDateTime date = LocalDateTime.now().minusDays(days);
        return lostItemReportRepository.findByReportDateAfter(date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 키워드로 제목 검색
    public List<LostItemReportDto> searchByTitle(String keyword) {
        return lostItemReportRepository.findByTitleContaining(keyword).stream()
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
                entity.getFoundTime()
        );
    }

    // DTO -> Entity 변환
    private LostItemReportEntity convertToEntity(LostItemReportDto dto) {
        return LostItemReportEntity.builder()
                .reportId(dto.getId())
                .itemName(dto.getTitle())
                .category(dto.getDescription())
                .foundLocation(dto.getFoundLocation())
                .reporterName(dto.getReporterName())
                .foundTime(dto.getReportDate())
                .build();
    }
}
