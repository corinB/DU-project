package com.project.dudu.entities;

import com.project.dudu.enums.ReservationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = DefaultListener.class)
@Table(name = "Reservation_T")
public class ReservationEntity implements IEntityAdapter<LocalDateTime>{ //당일 예약
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationType reservationType;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime reservationTime;


    //FK 설정
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    //FK 설정
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cabinet_id")
    private CabinetEntity cabinet;


    /**
     * 자동으로 예약종료일 설정
     **/
    @PrePersist
    private void setReservationTime() {
        if (this.reservationType != null) {
            this.reservationTime = LocalDateTime.now().plusDays(this.reservationType.getValue());
        }
    }
}
