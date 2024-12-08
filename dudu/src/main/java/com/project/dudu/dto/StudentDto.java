package com.project.dudu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dudu.entities.StudentEntity;
import com.project.dudu.enums.Colleges;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    @JsonProperty(value = "student_id")
    private Long studentId;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "student_name")
    private String studentName;

    @JsonProperty(value = "department")
    private String department;

    // 필드 값을 설정하는 생성자
    public StudentDto(Long studentId, String studentName, String password) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.password = password;
    }

    // department 문자열을 Colleges enum으로 변환하는 메서드
    public Colleges getCollege() {
        return Colleges.getByName(this.department);
    }

    public StudentEntity toEntity() { return StudentEntity.builder().studentId(this.studentId).studentName(this.studentName).password(this.password).department(this.getCollege()).build(); }

}
