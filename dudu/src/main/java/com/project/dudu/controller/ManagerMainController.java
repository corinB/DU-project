package com.project.dudu.controller;

import com.project.dudu.dto.LostItemReportDto;
import com.project.dudu.dto.ManagerDto;
import com.project.dudu.dto.ReservationDto;
import com.project.dudu.service.LostItemReportService;
import com.project.dudu.service.ManagerService;
import com.project.dudu.service.ReservationService;
import com.project.dudu.service.SearchService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 관리자 메인 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class ManagerMainController {

    private final ManagerService managerService;
    private final LostItemReportService lostItemReportService;
    private final SearchService searchService;
    private final ReservationService reservationService;


    // 관리자 메인 페이지 반환
    @GetMapping("/main")
    public String showManagerMain(HttpSession session, Model model) {
        ManagerDto manager = (ManagerDto) session.getAttribute("manager");
        if (manager == null) {
            // 세션에 관리자 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/manager/login";
        }
        model.addAttribute("managerName", manager.getName());
        return "ManagerMain"; // ManagerMain.html 반환
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/manager/login";
    }


    // 관리자 로그인 페이지 반환
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("managerDto", new ManagerDto());
        return "ManagerLogin"; // ManagerLogin.html 반환
    }

    // 관리자 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute("managerDto") ManagerDto managerDto, Model model, HttpSession session) {
        ManagerDto authenticatedManager = managerService.authenticate(managerDto.getEmail(), managerDto.getPassword());
        if (authenticatedManager != null) {
            // 로그인 성공 시 세션에 관리자 정보 저장
            session.setAttribute("manager", authenticatedManager);
            return "redirect:/manager/main"; // 관리자 메인 페이지로 리다이렉트
        } else {
            // 로그인 실패 시 에러 메시지 표시
            model.addAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "ManagerLogin"; // 로그인 페이지로 다시 이동
        }
    }

    // 분실물 신고 목록 페이지 반환
    @GetMapping("/lost-items")
    public String showLostItemReports(Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("reports", lostItemReportService.getAllReports());
        return "LostItemReport";  // LostItemReport.html 파일을 반환
    }

    // 분실물 신고 생성 페이지 반환
    @GetMapping("/lost-items/create")
    public String showCreateForm(HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }
        return "LostItemReportCreate";  // LostItemReportCreate.html 파일을 반환
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
        return "LostItemReportDetail";  // LostItemReportDetail.html 파일을 반환
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
        return "LostItemReport";  // 검색 결과를 목록 페이지에 표시
    }

    // 카테고리로 신고 검색
    @GetMapping("/lost-items/search/category")
    public String searchByCategory(@RequestParam String category, Model model, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("reports", lostItemReportService.searchByCategory(category));
        return "LostItemReport";  // 검색 결과를 목록 페이지에 표시
    }

    @GetMapping("/cabinet")
    public String findCabinet(@SessionAttribute(name = "manager", required = false) ManagerDto manager, Model model) {
        var dto = searchService.cabinetSearchByDepartment(manager.getDepartment());
        model.addAttribute("cabinets", dto);
        return "/managerPage/FindCabinet";
    }

    // 상태 업데이트 메서드 추가
    @PostMapping("/lost-items/{id}/updateStatus")
    public String updateLostItemStatus(@PathVariable Long id, @RequestParam String newStatus, HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "redirect:/manager/login";
        }
        lostItemReportService.updateReportStatus(id, newStatus);
        return "redirect:/manager/lost-items";
    }

    @GetMapping(value = "/student")
    public String findStudent(@SessionAttribute(name = "manager") ManagerDto manager,@RequestParam(name = "page", defaultValue = "0" ) int page ,Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("studentId").ascending());
        var studentList = searchService.searchForManager("department",manager.getDepartment(), pageable);
        model.addAttribute("studentList", studentList);
        model.addAttribute("page", page);
        return "/managerPage/FindStudent";
    }

}
