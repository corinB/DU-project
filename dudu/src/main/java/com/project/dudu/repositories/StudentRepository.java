package com.project.dudu.repositories;

import com.project.dudu.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    // 학번과 비밀번호로 학생 검색 (선택 사항)
    StudentEntity findByStudentIdAndPassword(Long studentId, String password);

}
