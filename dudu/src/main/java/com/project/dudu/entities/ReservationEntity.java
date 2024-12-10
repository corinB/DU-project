package com.project.dudu.entities;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
import com.project.dudu.entities.util.ReservationListener;
import com.project.dudu.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Configurable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Configurable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {DefaultListener.class, ReservationListener.class})
@Table(name = "Reservation_T")
public class ReservationEntity implements IEntityAdapter<LocalDateTime> {
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
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public boolean isDone(){
        return reservationTime.isBefore(LocalDateTime.now());
    }

    public ReservationDto toDto() {
        return ReservationDto.builder()
                .reservationId(this.reservationId)
                .studentId(this.student.getStudentId())
                .cabinetId(this.cabinet.getCabinetId())
                .reservationType(this.reservationType.name())
                .reservationStartTime(this.createAt)
                .reservationEndTime(this.reservationTime)
                .build();
    }

}
