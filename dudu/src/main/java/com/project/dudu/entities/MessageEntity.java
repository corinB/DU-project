package com.project.dudu.entities;

import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
import com.project.dudu.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EntityListeners(value = DefaultListener.class)
@Table(name = "message_T")
public class MessageEntity implements IEntityAdapter<LocalDateTime> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    //FK 설정
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ReservationId")
    private ReservationEntity reservation;



    @PrePersist
    @PreUpdate
    public void writeMSG(){
        var sName = reservation.getStudent().getStudentName();
        var cId = reservation.getCabinet().getCabinetId();
        switch (messageType){
            case Success -> {
                title = sName + "님 " + cId + "예약이 성공적으로 완료되었습니다.";
                content = "예약이 성공적으로 완료되었습니다.\n" +
                        "예약 시간: " + reservation.getCreateAt() + "\n" +
                        "예약 종료 시간: " + reservation.getReservationTime();
            }
            case Finish -> {
                title =  sName + "님 " + cId +"예약이 종료되었습니다.";
                content = "금일부로 예약이 종료되었습니다. \n";
                content = "예약이 금일부로 종료되었습니다.\n" +
                        "예약 시간: " + reservation.getCreateAt() + "\n" +
                        "예약 종료 시간: " + reservation.getReservationTime();
            }
            case Warning -> {}
            case Notice ->{
                title = sName + "님 " + cId + "예약종료일 안내드립니다.";
                content = "예약이 " + reservation.getReservationTime() + " 에 예약이 종료됨을 안내드립니다.";
            }
        }
    }

}
