package com.project.dudu.service;

import com.project.dudu.dto.StudentDto;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.Colleges;
import com.project.dudu.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // 회원가입 처리 메서드
    public StudentDto registerStudent(StudentDto studentDto) {
        // 입력값 검증
        if (studentDto.getStudentId() == null || studentDto.getStudentId() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 학번입니다.");
        }
        if (studentRepository.existsById(studentDto.getStudentId())) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다.");
        }

        // department 문자열을 Colleges enum으로 변환
        Colleges departmentEnum;
        try {
            departmentEnum = Colleges.valueOf(studentDto.getDepartment());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 학과입니다.");
        }

        // StudentEntity 생성
        StudentEntity studentEntity = StudentEntity.builder()
                .studentId(studentDto.getStudentId())
                .studentName(studentDto.getStudentName())
                .password(studentDto.getPassword())
                .department(departmentEnum)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        // 저장
        StudentEntity savedEntity = studentRepository.save(studentEntity);

        // 엔티티를 DTO로 변환하여 반환
        return convertToDto(savedEntity);
    }

    // 로그인(인증) 처리 로직
    public StudentDto authenticate(Long studentId, String password) {
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student != null && student.getPassword().equals(password)) {
            return convertToDto(student);
        }
        return null; // 인증 실패 시 null 반환
    }

    // Entity를 DTO로 변환하는 헬퍼 메서드
    private StudentDto convertToDto(StudentEntity entity) {
        return StudentDto.builder()
                .studentId(entity.getStudentId())
                .studentName(entity.getStudentName())
                .department(entity.getDepartment().getName()) // 열거형에서 한글 학과명 반환 가정
                .build();
    }
}
