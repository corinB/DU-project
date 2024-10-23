package com.project.dudu.repositories;

import com.project.dudu.entities.MessageEntity;
import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.enums.MessageType;
import com.project.dudu.enums.ReservationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MessageRepositoryTest {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CabinetRepository cabinetRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void addMessage() {
        studentRepository.findById(5L).ifPresent(studentEntity -> {
            cabinetRepository.findById(5L).ifPresent(cabinetEntity -> {
                var rEntity = ReservationEntity.builder().cabinet(cabinetEntity)
                        .student(studentEntity).reservationType(ReservationType.Day).build();
                var mEntity = MessageEntity.builder().messageType(MessageType.Success).reservation(rEntity).build();
                messageRepository.save(mEntity);
            });
        });
    }

}