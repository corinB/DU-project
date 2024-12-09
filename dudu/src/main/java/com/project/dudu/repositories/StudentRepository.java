package com.project.dudu.repositories;

import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.Colleges;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    // 학번과 비밀번호로 학생 검색 (선택 사항)
    StudentEntity findByStudentIdAndPassword(Long studentId, String password);

    @Query(value = "SELECT s FROM StudentEntity s")
    Page<StudentEntity> findAllPage(Pageable page);


    /**
     * 학과별 학생 찾기
     * @param department 학과
     * @param page 페이지
     *
     **/
    Page<StudentEntity> findAllByDepartment(Colleges department, Pageable page);


    /**
     * 학생명으로 학생 찾기
     **/
    Page<StudentEntity> findAllByStudentName(String studentName, Pageable page);


}
