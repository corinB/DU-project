package com.project.dudu.service;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.enums.ReservationType;
import com.project.dudu.repositories.CabinetRepository;
import com.project.dudu.repositories.ReservationRepository;
import com.project.dudu.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StudentRepository studentRepository;
    private final CabinetRepository cabinetRepository;


    /** 나중에 커스텀 익셉션으로 이미 빌린데 있음, 예약중인 사물함 이런거 넣읍시다
     * 예약 가능 검사 코드
     * @param studentId 학번
     * @param cabinetId 사물함 번호
     * @param reservationType 예약형태(일, 학기)
     * @return true: 예약 가능, false: 예약 불가
     **/
    @Transactional
    public boolean canReservation(long studentId, long cabinetId, ReservationType reservationType) {
        var studentReservationList = reservationRepository.findByStudentId(studentId);
        var cabinetReservationList = reservationRepository.findByCabinetId(cabinetId);

        // 학생 예약 중 조건에 맞는 예약이 있으면 True 반환
        boolean hasStudentReservation = studentReservationList.stream()
                .anyMatch(reservation -> reservation.getReservationType() == reservationType &&
                        reservation.getReservationTime().isAfter(LocalDateTime.now()));

        // 사물함 예약 중 조건에 맞는 예약이 있으면 True 반환
        boolean hasCabinetReservation = cabinetReservationList.stream()
                .anyMatch(reservation -> reservation.getReservationTime().isAfter(LocalDateTime.now()));

        return !(hasStudentReservation && hasCabinetReservation); // 조건이 하나라도 맞으면 false
    }


    /**
     * 케비닛 예약 함수
     * @param dto 예약형태, 학번, 사물함 번호 포함
     * @return ReservationDto: 예약형태, 학번, 사물함 번호, 예약시간 /null: 예약불가
     **/
    @Transactional
    public ReservationDto reserve(ReservationDto dto){
        if(canReservation(dto.getStudentId(), dto.getCabinetId(), dto.getReservationType())){
           studentRepository.findById(dto.getStudentId()).ifPresent(studentEntity -> {
               cabinetRepository.findById(dto.getCabinetId()).ifPresent(cabinetEntity -> {
              var reservation = reservationRepository.save(ReservationEntity.builder()
                       .student(studentEntity).cabinet(cabinetEntity)
                       .reservationType(dto.getReservationType()).build());

                   dto.setReservationStartTime(reservation.getCreateAt());
                   dto.setReservationEndTime(reservation.getReservationTime());
               });
           });
        return dto;
        }
        return null;
    }





}
