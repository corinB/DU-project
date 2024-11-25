package com.project.dudu.service;

import com.project.dudu.entities.MessageEntity;
import com.project.dudu.enums.MessageType;
import com.project.dudu.enums.ReservationType;
import com.project.dudu.repositories.MessageRepository;
import com.project.dudu.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final ReservationRepository reservationRepository;

    //-------------------------------------------------------------------------------------------------------------------
    /**
     * 예약 알림 작성 서비스
     * @param mType 메세지 타입
     *              - 예약일 알람
     *              -종료 알람
     * @param rType 예약 형태
     **/
    private void sendMessage(LocalDateTime time,MessageType mType, ReservationType rType) {
       reservationRepository.findReservationsAfterTimeByType(time, rType).forEach (reservationEntity ->{
           var massageEntity = MessageEntity.builder().reservation(reservationEntity).messageType(mType).build();
           messageRepository.save(massageEntity);
       });
    }
    //-------------------------------------------------------------------------------------------------------------------




    public void sendNotice(LocalDateTime time, ReservationType rType){
        sendMessage(time, MessageType.Notice, rType);
    }
}
