package com.project.dudu.service;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.entities.LostItemReportEntity;
import com.project.dudu.repositories.LostItemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LostItemReportService {

    private final LostItemReportRepository reportRepository;

    @Autowired
    public LostItemReportService(LostItemReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 분실물 신고 생성
    public LostItemReportDto createReport(LostItemReportDto dto) {
        LostItemReportEntity entity = new LostItemReportEntity();
        entity.setItemName(dto.getTitle());
        entity.setFoundLocation(dto.getLocation());
        entity.setFoundTime(dto.getReportDate());

        LostItemReportEntity savedEntity = reportRepository.save(entity);
        return convertToDto(savedEntity);
    }

    // ID로 신고 조회
    public Optional<LostItemReportDto> getReportById(Long id) {
        return reportRepository.findById(id).map(this::convertToDto);
    }

    // 모든 신고 조회
    public List<LostItemReportDto> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 신고자 이름으로 신고 검색
    public List<LostItemReportDto> getReportsByReporterName(String reporterName) {
        return reportRepository.findByReporterName(reporterName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 위치에 따라 신고 검색
    public List<LostItemReportDto> getReportsByLocation(String location) {
        return reportRepository.findByLocationContaining(location)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 최근 N일 이내의 신고 검색
    public List<LostItemReportDto> getRecentReports(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return reportRepository.findByReportDateAfter(cutoffDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 신고 수정
    public LostItemReportDto updateReport(Long id, LostItemReportDto dto) {
        Optional<LostItemReportEntity> optionalEntity = reportRepository.findById(id);

        if (optionalEntity.isPresent()) {
            LostItemReportEntity entity = optionalEntity.get();
            entity.setItemName(dto.getTitle());
            entity.setFoundLocation(dto.getLocation());
            entity.setFoundTime(dto.getReportDate());

            LostItemReportEntity updatedEntity = reportRepository.save(entity);
            return convertToDto(updatedEntity);
        }
        throw new RuntimeException("Report not found with id: " + id);
    }

    // 신고 삭제
    public void deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    // Entity를 DTO로 변환하는 헬퍼 메서드
    private LostItemReportDto convertToDto(LostItemReportEntity entity) {
        return new LostItemReportDto(
                entity.getReportId(),
                entity.getItemName(),
                "", // 설명은 비워둠 (또는 필요 시 구현)
                entity.getFoundLocation(),
                entity.getFoundPerson(),
                entity.getFoundTime()
        );
    }
}
