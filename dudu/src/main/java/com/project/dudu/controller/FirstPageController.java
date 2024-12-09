package com.project.dudu.controller;

import com.project.dudu.dto.ManagerDto;
import com.project.dudu.dto.StudentDto;
import com.project.dudu.service.ManageSignUpService;
import com.project.dudu.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FirstPageController {

    private final ManageSignUpService manageSignUpService;
    private final SignUpService signUpService;

    // 메인 페이지 반환
    @GetMapping("/dureservation")
    public String showMainPage() {
        return "mainpage";
    }

    // 학생 회원가입 페이지 반환
    @GetMapping("/student/signup")
    public String showStudentSignUpForm(Model model) {
        model.addAttribute("studentDto", StudentDto.builder().build());
        return "SignUp"; // SignUp.html 반환
    }

    // 학생 회원가입 처리
    @PostMapping("/student/signup")
    public String registerStudent(@ModelAttribute("studentDto") StudentDto studentDto, Model model) {
        signUpService.registerStudent(studentDto);
        model.addAttribute("message", "학생 회원가입이 성공적으로 완료되었습니다.");
        return "SignUpSuccess"; // 성공 메시지 페이지로 이동
    }

    // 관리자 회원가입 페이지 반환
    @GetMapping("/manager/signup")
    public String showManagerSignUpForm(Model model) {
        model.addAttribute("managerDto", ManagerDto.builder().build());
        return "ManagerSignUp"; // ManagerSignUp.html 반환
    }

    // 관리자 회원가입 처리
    @PostMapping("/manager/signup")
    public String registerManager(@ModelAttribute("managerDto") ManagerDto managerDto, Model model) {
        manageSignUpService.registerManager(managerDto);
        model.addAttribute("message", "관리자 회원가입이 성공적으로 완료되었습니다.");
        return "ManagerSignUpSuccess"; // 성공 메시지 페이지로 이동
    }
}