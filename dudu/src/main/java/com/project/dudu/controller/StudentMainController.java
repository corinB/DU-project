package com.project.dudu.controller;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.dto.StudentDto;
import com.project.dudu.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 학생 메인 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentMainController {

    private final StudentService studentLoginService;
    private final LostItemReportService lostItemReportService;
    private final StudentService signUpService;
    private final ReservationService reservationService;
    private final SearchService searchService;


//
//    // 회원가입 페이지 반환
//    @GetMapping("/signup")
//    public String showSignUpForm(Model model) {
//        model.addAttribute("studentId", null);
//        return "SignUp"; // SignUp.html 반환
//    }
//
//    // 회원가입 요청 처리
//    @PostMapping("/signup")
//    public String registerStudent(@ModelAttribute("studentDto") StudentDto studentDto, Model model) {
//        signUpService.registerStudent(studentDto);
//        model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
//        return "SignUpSuccess"; // 성공 메시지 페이지로 이동
//    }

    // 로그인 페이지 반환
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("studentId", null);
        model.addAttribute("studentDto", StudentDto.builder().build());
        return "StudentLogin"; // StudentLogin.html 반환
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute("studentDto") StudentDto studentDto, Model model, HttpSession session) {
        StudentDto authenticatedStudent = studentLoginService.authenticate(studentDto.getStudentId(), studentDto.getPassword());
        if (authenticatedStudent != null) {
            Long studentId = authenticatedStudent.getStudentId();
            // 로그인 성공 시 세션에 학생 정보 저장
            session.setAttribute("student", authenticatedStudent);
            session.setAttribute("studentId", studentId);
            return "redirect:/student/main"; // 학생 메인 페이지로 리다이렉트
        } else {
            // 로그인 실패 시 에러 메시지 표시
            model.addAttribute("errorMessage", "학번 또는 비밀번호가 올바르지 않습니다.");
            return "StudentLogin"; // 로그인 페이지로 다시 이동
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/student/login";
    }

    // 학생 메인 페이지 반환
    @GetMapping("/main")
    public String showMainPage(HttpSession session, Model model) {
        StudentDto student = (StudentDto) session.getAttribute("student");
        if (student == null) {
            return "redirect:/student/login";
        }
        model.addAttribute("studentName", student.getStudentName());
        return "StudentMain"; // StudentMain.html 반환
    }

    // 분실물 목록 조회 (학생용)
    @GetMapping("/lost-items")
    public String showLostItems(HttpSession session, Model model) {
        // 세션에서 학생 정보 확인
        if (session.getAttribute("student") == null) {
            return "redirect:/student/login";
        }

        // 분실물 목록 조회
        model.addAttribute("lostItems", lostItemReportService.getAllReports());

        return "StudentLostItems"; // 학생용 분실물 목록 페이지
    }

//-------------------------------------------------------------------------------------------------------------

    // 추가로 필요한 학생 기능들을 이곳에 작성하면 됩니다.
       /**
        * 학생 예약 페이지
        * @param student 세션에 있는 정보 확인
        *                       null 이면 로그인 페이지로 이동
        * @return Reservation
        **/
        @GetMapping("/reserve")
        public String reservation(@SessionAttribute(name = "student", required = false) StudentDto student) {
            if (student == null) return "redirect:/student/login";
            return "StudentReserve";
        }
    //-------------------------------------------------------------------------------------------------------------
        /**
         * 예약 처리
         * @param studentId 세션의 학번 확인
         * @param reservationDto 예약정보
         * @param model Model
         * @return StudentReserve
         **/
    @PostMapping("/reserve")
    public String tryReservation(@SessionAttribute(name = "studentId", required = false) Long studentId,
                                 @ModelAttribute ReservationDto reservationDto, Model model) {
        reservationDto.setStudentId(studentId);
        var dto = reservationService.reserve(reservationDto);
        if (dto == null) return "FailRes";
        model.addAttribute("dto", dto);
        return "SuccessRes";
    }

    @GetMapping("/my-info")
    public String findMyInfo (@SessionAttribute(name = "student") StudentDto student, Model model) {
        var studentId = student.getStudentId();
        var dto = searchService.searchByStudentId(studentId);
        model.addAttribute("dto", dto);
        return "/StudentPage/MyInfo";
    }
}