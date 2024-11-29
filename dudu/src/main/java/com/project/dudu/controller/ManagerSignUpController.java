package com.project.dudu.controller;

import com.project.dudu.dto.ManagerDto;
import com.project.dudu.service.ManageSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 관리자 회원가입 컨트롤러
@Controller
@RequestMapping("/manager/signup")
public class ManagerSignUpController {

    private final ManageSignUpService manageSignUpService;

    @Autowired
    public ManagerSignUpController(ManageSignUpService manageSignUpService) {
        this.manageSignUpService = manageSignUpService;
    }

    // 회원가입 페이지 반환
    @GetMapping
    public String showManagerSignUpForm(Model model) {
        model.addAttribute("managerDto", new ManagerDto());
        return "ManagerSignUp"; // ManagerSignUp.html 반환
    }

    // 회원가입 요청 처리
    @PostMapping
    public String registerManager(@ModelAttribute("managerDto") ManagerDto managerDto, Model model) {
        manageSignUpService.registerManager(managerDto);
        model.addAttribute("message", "관리자 회원가입이 성공적으로 완료되었습니다.");
        return "ManagerSignUpSuccess"; // 성공 메시지 페이지로 리다이렉트
    }
}
