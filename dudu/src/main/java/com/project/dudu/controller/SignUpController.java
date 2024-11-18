package com.project.dudu.controller;

import com.project.dudu.dto.StudentDto;
import com.project.dudu.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    // 회원가입 페이지 반환
    @GetMapping
    public String showSignUpForm() {
        return "SignUp"; // SignUp.html 반환
    }

    // 회원가입 요청 처리
    @PostMapping
    public String registerStudent(@ModelAttribute StudentDto studentDto, Model model) {
        signUpService.registerStudent(studentDto);
        model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
        return "SignUpSuccess"; // 성공 메시지 페이지로 리다이렉트
    }
}
