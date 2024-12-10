package com.project.dudu.service;

import com.project.dudu.dto.CabinetDto;
import com.project.dudu.dto.ReservationDto;
import com.project.dudu.dto.StudentDto;
import com.project.dudu.entities.CabinetEntity;
import com.project.dudu.entities.ReservationEntity;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.Colleges;
import com.project.dudu.repositories.CabinetRepository;
import com.project.dudu.repositories.ReservationRepository;
import com.project.dudu.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final StudentRepository studentRepository;
    private final ReservationRepository reservationRepository;
    private final CabinetRepository cabinetRepository;


    //-------------------------------------------------------------------------------------------------

    /**
     * 학생 전체 검색
     * @param pageable 검색조건
     *                 - page, size, sort
     * **/
    public Page<StudentDto> searchAllStudent(Pageable pageable) {
        var findPage = studentRepository.findAllPage(pageable);
        return findPage.map(StudentEntity::toDto);
    }

    /**
     * 학생 이름 검색
     * @param pageable 검색조건
     *                 - page, size, sort
     * @param name     검색어
     **/
    public Page<StudentDto> searchByName(String name, Pageable pageable) {
        var findPage = studentRepository.findAllByStudentName(name, pageable);
        return findPage.map(StudentEntity::toDto);
    }

    /**
     * 학생 학과 검색
     * @param pageable 검색조건
     *                 - page, size, sort
     * @param department 검색어
     **/
    public Page<StudentDto> searchByDepartment(String department, Pageable pageable) {
        var college = Colleges.getByName(department);
        var findPage = studentRepository.findAllByDepartment(college, pageable);
        return findPage.map(StudentEntity::toDto);
    }

    public StudentDto searchByStudentId(Long studentId) {
        return studentRepository.findById(studentId).map(StudentEntity::toDto).orElse(null);
    }

    @Transactional
    public Page<StudentDto> searchForManager(String type, String searchWord, Pageable pageable) {
       return switch (type) {
           case "all"-> searchAllStudent(pageable);
           case "name" -> searchByName(searchWord, pageable);
           case "department" -> searchByDepartment(searchWord, pageable);
           default -> null;
        };
    }

    //*******************************************************************************

    @Transactional
    public Page<ReservationDto> findStudentReservation(Long studentId, Pageable pageable) {
        return reservationRepository.findAllByStudentId(studentId, pageable).map(ReservationEntity::toDto);
    }
    //*******************************************************************************************

    @Transactional
    public Page<CabinetDto> cabinetSearch(Pageable pageable) {
        var cabinets = cabinetRepository.findAll(pageable);
        return cabinets.map(CabinetEntity::toDto);
    }

    @Transactional
    public List<CabinetDto> cabinetSearchByDepartment(String department) {
        var college = Colleges.getByName(department);
        var cabinets = cabinetRepository.findAllByDepartment(college);
        return cabinets.stream().map(CabinetEntity::toDto).toList();
    }

}
