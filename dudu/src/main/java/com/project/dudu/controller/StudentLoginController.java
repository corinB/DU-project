package com.project.dudu.controller;

import com.project.dudu.dto.StudentDto;
import com.project.dudu.service.StudentLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 학생 로그인 컨트롤러
@Controller
@RequestMapping("/student/login")
public class StudentLoginController {

    private final StudentLoginService studentLoginService;

    @Autowired
    public StudentLoginController(StudentLoginService studentLoginService) {
        this.studentLoginService = studentLoginService;
    }

    // 로그인 페이지 반환
    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        return "StudentLogin"; // StudentLogin.html 반환
    }

    // 로그인 처리
    @PostMapping
    public String login(@ModelAttribute("studentDto") StudentDto studentDto, Model model, HttpSession session) {
        StudentDto authenticatedStudent = studentLoginService.authenticate(studentDto.getStudentId(), studentDto.getPassword());
        if (authenticatedStudent != null) {
            // 로그인 성공 시 세션에 학생 정보 저장
            session.setAttribute("student", authenticatedStudent);
            return "redirect:/student/dashboard"; // 학생 대시보드로 리다이렉트
        } else {
            // 로그인 실패 시 에러 메시지 표시
            model.addAttribute("errorMessage", "학번 또는 비밀번호가 올바르지 않습니다.");
            return "StudentLogin"; // 로그인 페이지로 다시 이동
        }
    }

    @GetMapping("/student/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/student/login";
    }
}
