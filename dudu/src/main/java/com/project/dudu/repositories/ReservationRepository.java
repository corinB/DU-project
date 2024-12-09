package com.project.dudu.repositories;

import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.ReservationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


//-------------------------------------------------------------------------------------------------------------------------------------------------

    String findByStudentIdQuery = "select r from ReservationEntity r where r.student.studentId = :studentId";
    /**
     * 학번으로 찾기
     * @param studentId 학번
     * @return List<ReservationEntity> 학생 예약 내역
     **/
    @Query(value = findByStudentIdQuery)
    List<ReservationEntity> findByStudentId(long studentId);

//-------------------------------------------------------------------------------------------------------------------------------------------------

    String findByCabinetIdQuery = "select r from ReservationEntity r where r.cabinet.cabinetId = :cabinetId";
    /**
     * 예약을 찾기
     * @param cabinetId 예약
     * @return List<ReservationEntity> 예약 내역
     **/
    @Query(value = findByCabinetIdQuery)
    List<ReservationEntity> findByCabinetId(long cabinetId);

//-------------------------------------------------------------------------------------------------------------------------------------------------

    String findByReservationAndTypeAfterTime = "select r from ReservationEntity r "+
            "where r.reservationTime > :time and r.reservationType = :type";
    /**
     * 주어진 시간 이전의 특정 타입의 미종료 예약 찾기
     * @param time 조회 기준 시간
     * @param type 조회할 예약 타입
     * @return 예약 내역 리스트
     */
    @Transactional(readOnly = true)
    @Query(value = findByReservationAndTypeAfterTime)
    List<ReservationEntity> findReservationsAfterTimeByType(LocalDateTime time, ReservationType type);

//-------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 메니저페이지에서 학생 예약 찾기
     * @param studentId 학생
     * @param page 페이지
     *                 null 이면 1 페이지로 이동
     * @return List<ReservationEntity> 학생 예약 내역
     **/
    String findAllByStudentIdQuery = "select r from ReservationEntity r where r.student.studentId = :studentId";
    @Query(value = findAllByStudentIdQuery)
    Page<ReservationEntity> findAllByStudentId(Long studentId, Pageable page);
}
