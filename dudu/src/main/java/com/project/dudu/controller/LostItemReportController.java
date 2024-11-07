package com.project.dudu.controller;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.service.LostItemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
