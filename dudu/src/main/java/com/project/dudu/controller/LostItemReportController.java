package com.project.dudu.controller;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.service.LostItemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/LostItems")
public class LostItemReportController {
    private final LostItemReportService lostItemReportService;

    @Autowired
    public LostItemReportController(LostItemReportService lostItemReportService) {
        this.lostItemReportService = lostItemReportService;
    }

    // 분실물 신고 생성 메서드
    @PostMapping
    public ResponseEntity<LostItemReportDto> createReport(@RequestBody LostItemReportDto lostItemReportDto) {
        LostItemReportDto createReport = lostItemReportService.createReport(lostItemReportDto);
        return new ResponseEntity<>(createReport, HttpStatus.CREATED);
    }

    // 모든 분실물 신고 조회
    @GetMapping
    public ResponseEntity<List<LostItemReportDto>> getAllReports() {
        List<LostItemReportDto> lostItems = lostItemReportService.getAllReports();
        return new ResponseEntity<>(lostItems, HttpStatus.OK);
    }

    // 특정 ID로 분실물 신고 조회
    @GetMapping("/{id}")  //차후 수정
    public ResponseEntity<LostItemReportDto> getReportById(@PathVariable Long id) {
        // Optional 처리에 맞게 수정
        Optional<LostItemReportDto> lostItemReport = lostItemReportService.getReportById(id);

        // Optional의 존재 여부를 체크하고 ResponseEntity를 반환  이 부분 조금 애매
        return lostItemReport
                .map(report -> new ResponseEntity<>(report, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 특정 ID의 분실물 신고 업데이트
    @PutMapping("/{id}") //차후 수정
    public ResponseEntity<LostItemReportDto> updateReport(@PathVariable Long id, @RequestBody LostItemReportDto lostItemReportDto) {
        LostItemReportDto updatedReport = lostItemReportService.updateReport(id, lostItemReportDto);
        if (updatedReport != null) {
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 특정 ID의 분실물 신고를 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            lostItemReportService.deleteReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
