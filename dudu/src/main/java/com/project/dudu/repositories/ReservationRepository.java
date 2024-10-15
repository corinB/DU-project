package com.project.dudu.repositories;

import com.project.dudu.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    String findByStudentIdQuery = "select r from ReservationEntity r where r.student.studentId = :studentId";
    /**
     * 학번으로 찾기
     * @param studentId 학번
     * @return List<ReservationEntity> 학생 예약 내역
     **/
    @Query(value = findByStudentIdQuery)
    List<ReservationEntity> findByStudentId(long studentId);



    String findByCabinetIdQuery = "select r from ReservationEntity r where r.cabinet.cabinetId = :cabinetId";
    /**
     * 예약을 찾기
     * @param cabinetId 예약
     * @return List<ReservationEntity> 예약 내역
     **/
    @Query(value = findByCabinetIdQuery)
    List<ReservationEntity> findByCabinetId(long cabinetId);
}
