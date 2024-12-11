package com.project.dudu.controller;


import com.project.dudu.dto.CabinetDto;
import com.project.dudu.dto.ReservationDto;
import com.project.dudu.dto.StudentDto;
import com.project.dudu.service.ReservationService;
import com.project.dudu.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;
    private final ReservationService reservationService;

    /**
     * Manager Search
     * @param type     - all, name, department
     *
     * @param keyword  - keyword
     *
     * @param sort     - sort(정열기준)
     *                      - studentId, studentName, name, department, createAt, updateAt
     * @param page     - page(현재페이지)
     * @param size     - size(한페이지 크기)
     *
     **/
    @GetMapping("/manager")
    public Page<StudentDto> managerSearch (@RequestParam(name = "type") String type,
                                           @RequestParam(name = "keyword") String keyword,
                                           @RequestParam(name ="sort", defaultValue = "studentId" ) String sort,
                                           @RequestParam(name = "page", defaultValue = "0" ) int page,
                                           @RequestParam(name = "size" , defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        return searchService.searchForManager(type, keyword, pageable);
    }

    /**
     * Manager Search
     * @param studentId - studentId
     *
     **/
    @GetMapping(value = "/manager", params = "student-id")
    public StudentDto managerSearch (@RequestParam(name = "student-id") Long studentId) {
        return searchService.searchByStudentId(studentId);
    }


    @GetMapping("/reservation")
    public Page<ReservationDto> findStudentReservation (@RequestParam(name = "student-id") Long studentId,
                                                        Pageable pageable) {
        var student = searchService.searchByStudentId(studentId);
        log.info("student : {}", student);
        return searchService.findStudentReservation(studentId, pageable);
    }

    @GetMapping(value = "/cabinet/reservation", params = "cabinet-id")
    public List<ReservationDto> findReservation(@RequestParam(name = "cabinet-id") Long cabinetId) {
        log.info("findReservation \n cabinetId : {}", cabinetId);
        return reservationService.findAllByCabinetId(cabinetId);
    }

    @GetMapping(value = "/student/reservation", params = "student-id")
    public Page<ReservationDto> findReservation_student(@RequestParam(name = "student-id") Long studentId,
                                                        @RequestParam(name = "page", defaultValue = "0" ) int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createAt").descending());
        log.info("findReservation \n studentId : {}", studentId);
        return searchService.findStudentReservation(studentId, pageable);
    }
}
