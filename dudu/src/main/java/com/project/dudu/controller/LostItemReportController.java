package com.project.dudu.controller;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.service.LostItemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/lost-items")
public class LostItemReportController {

    private final LostItemReportService lostItemReportService;

    @Autowired
    public LostItemReportController(LostItemReportService lostItemReportService) {
        this.lostItemReportService = lostItemReportService;
    }

    // 분실물 신고 목록 페이지 반환
    @GetMapping
    public String showLostItemReports(Model model) {
        model.addAttribute("reports", lostItemReportService.getAllReports());
        return "LostItemReport";  // LostItemReport.html 파일을 반환
    }

    // 분실물 신고 생성 페이지 반환
    @GetMapping("/create")
    public String showCreateForm() {
        return "LostItemReportCreate";  // LostItemReportCreate.html 파일을 반환
    }

    // 분실물 신고 생성 요청 처리
    @PostMapping("/create")
    public String createLostItemReport(@ModelAttribute LostItemReportDto lostItemReportDto) {
        if (lostItemReportDto.getFoundLocation() == null || lostItemReportDto.getFoundLocation().isEmpty()) {
            throw new IllegalArgumentException("Found location cannot be null or empty.");
        }

        lostItemReportService.createReport(lostItemReportDto);
        return "redirect:/lost-items";
    }

    // 특정 ID로 분실물 신고 조회 페이지 반환
    @GetMapping("/{id}")
    public String getLostItemReportById(@PathVariable Long id, Model model) {
        LostItemReportDto report = lostItemReportService.getReportById(id).orElseThrow(() ->
                new IllegalArgumentException("Report with ID " + id + " not found."));
        model.addAttribute("report", report);
        return "LostItemReportDetail";  // LostItemReportDetail.html 파일을 반환
    }

    // 분실물 신고 삭제 요청 처리
    @PostMapping("/{id}/delete")
    public String deleteLostItemReport(@PathVariable Long id) {
        lostItemReportService.deleteReport(id);
        return "redirect:/lost-items";
    }

    // 위치로 신고 검색
    @GetMapping("/search")
    public String searchByLocation(@RequestParam String foundLocation, Model model) {
        model.addAttribute("reports", lostItemReportService.searchByLocation(foundLocation));
        return "LostItemReport";  // 검색 결과를 목록 페이지에 표시
    }

    // 카테고리로 신고 검색 (새로 추가된 메서드)
    @GetMapping("/search/category")
    public String searchByCategory(@RequestParam String category, Model model) {
        model.addAttribute("reports", lostItemReportService.searchByCategory(category));
        return "LostItemReport";  // 검색 결과를 목록 페이지에 표시
    }
}
