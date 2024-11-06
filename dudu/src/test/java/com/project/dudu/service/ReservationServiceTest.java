package com.project.dudu.service;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.enums.ReservationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void canReservation() {
        System.out.println(reservationService.canReservation(0L, 0L, ReservationType.Semester));
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void reserve() {
        var dto = ReservationDto.builder().studentId(2L).cabinetId(3L).reservationType("day").build();
        var result = reservationService.reserve(dto);
        System.out.println(result);
    }
}