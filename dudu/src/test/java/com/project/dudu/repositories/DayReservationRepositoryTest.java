package com.project.dudu.repositories;

import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.enums.ReservationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DayReservationRepositoryTest {
    @Autowired CabinetRepository cabinetRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void addReservation() {
        cabinetRepository.findById(0L).ifPresent(cabinetEntity -> {
            studentRepository.findById(0L).ifPresent(studentEntity -> {
                var dayReservation = ReservationEntity.builder().cabinet(cabinetEntity).student(studentEntity).build();
                reservationRepository.save(dayReservation);
            });
        });
        cabinetRepository.findById(1L).ifPresent(cabinetEntity -> {
            studentRepository.findById(1L).ifPresent(studentEntity -> {
                var Reservation = ReservationEntity.builder().cabinet(cabinetEntity).student(studentEntity).build();
                reservationRepository.save(Reservation);
            });
        });
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void findReservation() {
        reservationRepository.findByStudentId(0L).forEach(dayReservationEntity ->{
            System.out.println(dayReservationEntity.getStudent().getStudentName());
        } );
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void semesterReservation() {
        studentRepository.findById(0L).ifPresent(studentEntity -> {
           cabinetRepository.findById(0L).ifPresent(cabinetEntity -> {
               reservationRepository.save(
                       ReservationEntity.builder()
                               .cabinet(cabinetEntity).student(studentEntity)
                               .reservationType(ReservationType.Semester).build());
           });
        });
    }
}