package com.project.dudu.dto;

import com.project.dudu.enums.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long studentId;
    private Long cabinetId;
    private String reservationType;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;


    /**
     * 예약 형태 반환
     * @return ReservationType
     **/
    public ReservationType getReservationTypeEnum() {
        return ReservationType.getByType(this.reservationType);
    }
}
