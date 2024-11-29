package com.project.dudu.controller;

import com.project.dudu.dto.ManagerDto;
import com.project.dudu.service.ManagerLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 관리자 로그인 컨트롤러
@Controller
@RequestMapping("/manager/login")
public class ManagerLoginController {

    private final ManagerLoginService managerLoginService;

    @Autowired
    public ManagerLoginController(ManagerLoginService managerLoginService) {
        this.managerLoginService = managerLoginService;
    }

    // 로그인 페이지 반환
    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("managerDto", new ManagerDto());
        return "ManagerLogin"; // ManagerLogin.html 반환
    }

    // 로그인 처리 (수정된 메서드)
    @PostMapping
    public String login(@ModelAttribute("managerDto") ManagerDto managerDto, Model model, HttpSession session) {
        ManagerDto authenticatedManager = managerLoginService.authenticate(managerDto.getEmail(), managerDto.getPassword());
        if (authenticatedManager != null) {
            // 로그인 성공 시 세션에 관리자 정보 저장
            session.setAttribute("manager", authenticatedManager);
            return "redirect:/manager/dashboard"; // 관리자 대시보드로 리다이렉트
        } else {
            // 로그인 실패 시 에러 메시지 표시
            model.addAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "ManagerLogin"; // 로그인 페이지로 다시 이동
        }
    }

    @GetMapping("/manager/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/manager/login";
    }
}
