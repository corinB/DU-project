package com.project.dudu.entities.util;

import com.project.dudu.entities.ReservationEntity;
import jakarta.persistence.PrePersist;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationListener {
    private final DateTimeFormatter formatter;

    public ReservationListener() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    @PrePersist
    public void setReservationTime(ReservationEntity reservation) {
        switch (reservation.getReservationType()) {
            case Day -> reservation.setReservationTime( LocalDateTime.now()
                    .withHour(19)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0)); // 당일 오후 7시로 설정

            case Semester -> {
                String date = reservation.getReservationType().getDate();
                reservation.setReservationTime(LocalDateTime.parse(date, formatter));
            }
        }
    }
}
