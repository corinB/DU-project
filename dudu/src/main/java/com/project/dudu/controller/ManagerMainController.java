package com.project.dudu.controller;

import com.project.dudu.dto.ManagerDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerMainController {

    @GetMapping("/manager/main")
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
    @GetMapping("/manager/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/manager/login";
    }
}
