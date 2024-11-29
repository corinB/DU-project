package com.project.dudu.service;

import com.project.dudu.dto.StudentDto;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentLoginService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentLoginService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 인증 로직
    public StudentDto authenticate(Long studentId, String password) {
        // 학번으로 학생 정보 조회
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            // 비밀번호 확인 (비밀번호 암호화가 되어 있다면 해시 비교 로직 필요)
            if (student.getPassword().equals(password)) {
                // 인증 성공 시 StudentDto로 변환하여 반환
                return convertToDto(student);
            }
        }
        return null; // 인증 실패 시 null 반환
    }

    // Entity를 DTO로 변환하는 메서드
    private StudentDto convertToDto(StudentEntity entity) {
        return StudentDto.builder()
                .studentId(entity.getStudentId())
                .studentName(entity.getStudentName())
                .department(entity.getDepartment().getName())
                .build();
    }
}