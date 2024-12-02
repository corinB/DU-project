package com.project.dudu.controller;

import com.project.dudu.dto.StudentDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentMainController {

    @GetMapping("/student/main")
    public String showStudentMain(HttpSession session, Model model) {
        StudentDto student = (StudentDto) session.getAttribute("student");
        if (student == null) {
            // 세션에 학생 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/student/login";
        }
        model.addAttribute("studentName", student.getStudentName());
        return "StudentMain"; // StudentMain.html 반환
    }

    // 로그아웃 처리
    @GetMapping("/student/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/student/login";
    }
}
