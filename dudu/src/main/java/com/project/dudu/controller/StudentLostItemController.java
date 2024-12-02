package com.project.dudu.controller;

import com.project.dudu.service.LostItemReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 학생용 분실물 목록 컨트롤러
@Controller
public class StudentLostItemController {

    private final LostItemReportService lostItemReportService;

    @Autowired
    public StudentLostItemController(LostItemReportService lostItemReportService) {
        this.lostItemReportService = lostItemReportService;
    }

    @GetMapping("/student/lost-items")
    public String showLostItems(HttpSession session, Model model) {
        // 세션에서 학생 정보 확인
        if (session.getAttribute("student") == null) {
            return "redirect:/student/login";
        }

        // 분실물 목록 조회
        model.addAttribute("lostItems", lostItemReportService.getAllReports());

        return "StudentLostItems"; // 학생용 분실물 목록 페이지
    }
}
