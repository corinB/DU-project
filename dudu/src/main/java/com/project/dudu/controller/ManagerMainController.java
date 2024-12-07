package com.project.dudu.controller;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.dto.ManagerDto;
import com.project.dudu.service.LostItemReportService;
import com.project.dudu.service.ManageSignUpService;
import com.project.dudu.service.ManagerLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerMainController {

    private final ManagerLoginService managerLoginService;
    private final ManageSignUpService manageSignUpService;
    private final LostItemReportService lostItemReportService;

    // 관리자 메인 페이지 반환
    @GetMapping("/main")
    public String showManagerMain(HttpSession session, Model model) {
        ManagerDto manager = (ManagerDto) session.getAttribute("manager");
        if (manager == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("managerName", manager.getName());
        return "ManagerMain"; // ManagerMain.html
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/manager/login";
    }

    // 관리자 로그인 페이지 반환
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("managerDto", new ManagerDto());
        return "ManagerLogin"; // ManagerLogin.html
    }

    // 관리자 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute("managerDto") ManagerDto managerDto, Model model, HttpSession session) {
        ManagerDto authenticatedManager = managerLoginService.authenticate(managerDto.getEmail(), managerDto.getPassword());
        if (authenticatedManager != null) {
            session.setAttribute("manager", authenticatedManager);
            return "redirect:/manager/main";
        } else {
            model.addAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "ManagerLogin";
        }
    }

    // 분실물 신고 목록 페이지 반환
    @GetMapping("/lost-items")
    public String showLostItemReports(Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("reports", lostItemReportService.getAllReports());
        return "LostItemReport"; // LostItemReport.html
    }

    // 분실물 신고 생성 페이지 반환
    @GetMapping("/lost-items/create")
    public String showCreateForm(HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }
        return "LostItemReportCreate"; // LostItemReportCreate.html
    }

    // 분실물 신고 생성 요청 처리
    @PostMapping("/lost-items/create")
    public String createLostItemReport(@ModelAttribute LostItemReportDto lostItemReportDto, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        if (lostItemReportDto.getFoundLocation() == null || lostItemReportDto.getFoundLocation().isEmpty()) {
            throw new IllegalArgumentException("습득 장소는 필수 입력 사항입니다.");
        }

        lostItemReportService.createReport(lostItemReportDto);
        return "redirect:/manager/lost-items";
    }

    // 특정 ID로 분실물 신고 조회 페이지 반환
    @GetMapping("/lost-items/{id}")
    public String getLostItemReportById(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        LostItemReportDto report = lostItemReportService.getReportById(id).orElseThrow(() ->
                new IllegalArgumentException("ID가 " + id + "인 신고를 찾을 수 없습니다."));
        model.addAttribute("report", report);
        return "LostItemReportDetail"; // LostItemReportDetail.html
    }

    // 분실물 신고 삭제 요청 처리
    @PostMapping("/lost-items/{id}/delete")
    public String deleteLostItemReport(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        lostItemReportService.deleteReport(id);
        return "redirect:/manager/lost-items";
    }

    // 위치로 신고 검색
    @GetMapping("/lost-items/search")
    public String searchByLocation(@RequestParam String foundLocation, Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("reports", lostItemReportService.searchByLocation(foundLocation));
        return "LostItemReport";
    }

    // 카테고리로 신고 검색
    @GetMapping("/lost-items/search/category")
    public String searchByCategory(@RequestParam String category, Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("reports", lostItemReportService.searchByCategory(category));
        return "LostItemReport";
    }
}


