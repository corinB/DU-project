package com.project.dudu.service;
import com.project.dudu.dto.StudentDto;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignUpService {

    private final StudentRepository studentRepository;

    @Autowired
    public SignUpService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 회원가입 처리 메서드
    public StudentDto registerStudent(StudentDto studentDto) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentName(studentDto.getStudentName());
        studentEntity.setPassword(studentDto.getPassword());

        StudentEntity savedEntity = studentRepository.save(studentEntity);
        return convertToDto(savedEntity);
    }

    // Entity를 DTO로 변환하는 헬퍼 메서드
    private StudentDto convertToDto(StudentEntity entity) {
        return new StudentDto(
                entity.getStudentId(),
                entity.getStudentName(),
                entity.getPassword()
        );
    }
}
