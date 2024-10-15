package com.project.dudu.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student_T")
public class StudentEntity {
    @Id
    private long studentId;
    private String password;
    private String studentName;
    // 나중에 ENUM 단대 추가



}
