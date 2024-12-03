package com.project.dudu.controller;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;


    @GetMapping
    public String reservation() {
        return "Reservation";
    }


    @PostMapping("/try")
    public String tryReservation(@ModelAttribute ReservationDto reservationDto, Model model){
        var dto = reservationService.reserve(reservationDto);
        if (dto == null) return "FailRes";
        model.addAttribute("dto", dto);
        return "SuccessRes";
    }



}
