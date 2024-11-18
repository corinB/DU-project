package com.project.dudu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dudu.enums.Colleges;
import com.project.dudu.enums.ReservationType;
import lombok.*;
import org.hibernate.annotations.Collate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentDto {
    @JsonProperty(value = "student_id")
    private Long studentId;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "student_name")
    private String studentName;

    @JsonProperty(value = "department")
    private String department;

    public StudentDto(long studentId, String studentName, String password) {
    }

    public Colleges getCollege() {
        return Colleges.getByName(this.department);
    }


}
