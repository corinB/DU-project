package com.project.dudu.repositories;

import com.project.dudu.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
