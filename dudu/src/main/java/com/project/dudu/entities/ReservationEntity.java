package com.project.dudu.entities;

import com.project.dudu.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = DefaultListener.class)
@Table(name = "Reservation_T")
public class ReservationEntity implements IEntityAdapter<LocalDateTime>{
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    //FK 설정
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cabinet_id")
    private CabinetEntity cabinet;

    //PK 설정
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @Builder.Default
    private List<MessageEntity> messageList = new ArrayList<>();


    /**
     * 자동으로 예약종료일 설정
     **/
    @PrePersist
    private void setReservationTime() {
        switch (this.reservationType) {
            case Day -> this.reservationTime = LocalDateTime.now()
                    .withHour(19)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0); // 당일 오후 7시로 설정

            case Semester -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String date = this.reservationType.getDate();
                this.reservationTime = LocalDateTime.parse(date, formatter);
            }
        }
    }
}
