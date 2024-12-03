package com.project.dudu.service;

import com.project.dudu.dto.ReservationDto;
import com.project.dudu.entities.MessageEntity;
import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.enums.MessageType;
import com.project.dudu.enums.ReservationType;
import com.project.dudu.repositories.CabinetRepository;
import com.project.dudu.repositories.MessageRepository;
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
    private final MessageRepository messageRepository;

//----------------------------------------------------------------------------------------------------------------------

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

        // 학생 예약 중 조건에 맞는 예약이 있으면 true 반환 (즉, 예약 불가)
        boolean hasStudentReservation = studentReservationList.stream()
                .anyMatch(reservation -> reservation.getReservationType() == reservationType &&
                        reservation.getReservationTime().isAfter(LocalDateTime.now()));

        // 사물함 예약 중 조건에 맞는 예약이 있으면 true 반환 (즉, 예약 불가)
        boolean hasCabinetReservation = cabinetReservationList.stream()
                .anyMatch(reservation -> reservation.getReservationTime().isAfter(LocalDateTime.now()));

        // 둘 중 하나라도 예약 중이라면 false 반환 (예약 불가)
        return !(hasStudentReservation || hasCabinetReservation);
    }


//----------------------------------------------------------------------------------------------------------------------


    /**
     * 케비닛 예약 함수
     * @param dto 예약형태, 학번, 사물함 번호 포함
     * @return ReservationDto: 예약형태, 학번, 사물함 번호, 예약시간 /null: 예약불가
     **/
    @Transactional
    public ReservationDto reserve(ReservationDto dto){
        if(canReservation(dto.getStudentId(), dto.getCabinetId(), dto.getReservationTypeEnum())) {
            var reservationEntity = reservationDtoToEntity(dto);
            var messageEntity = makeSuccessMessage(reservationEntity);
            messageRepository.save(messageEntity);
            return ReservationEntityToDto(reservationRepository.save(reservationEntity));
        }else return null;
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * ReservationEntity -> ReservationDto
     * @param entity ReservationEntity
     *                  - 예약형태, 학번, 사물함 번호, 예약시간
     * @return ReservationDto
     **/
    private ReservationDto ReservationEntityToDto(ReservationEntity entity){
        return ReservationDto.builder()
                .reservationType(entity.getReservationType().name())
                .studentId(entity.getStudent().getStudentId())
                .cabinetId(entity.getCabinet().getCabinetId()).build();
    }
//----------------------------------------------------------------------------------------------------------------------

  /**
   * ReservationDto -> ReservationEntity
   * @param dto ReservationDto
   *                  - 예약형태, 학번, 사물함 번호, 예약시간
   * @return ReservationEntity
   **/
    private ReservationEntity reservationDtoToEntity(ReservationDto dto){
        ReservationType rType = ReservationType.getByType(dto.getReservationType());
        return ReservationEntity.builder()
                .cabinet(cabinetRepository.findById(dto.getCabinetId()).get())
                .student(studentRepository.findById(dto.getStudentId()).get())
                .reservationType(rType)
                .build();
    }

    private MessageEntity makeSuccessMessage(ReservationEntity entity){
        return MessageEntity.builder()
                .reservation(entity)
                .messageType(MessageType.Success)
                .build();

    }

}
