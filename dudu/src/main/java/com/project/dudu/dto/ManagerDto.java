package com.project.dudu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dudu.enums.Colleges;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;  // 관리자 이메일

    @JsonProperty("password")
    private String password;  // 관리자 비밀번호

    @JsonProperty("name")
    private String name;  // 관리자 이름

    @JsonProperty("department")
    private String department;  // 소속 대학 (한글 이름)

    // department 문자열을 Colleges enum으로 변환하는 메서드
    public Colleges getDepartmentEnum() {
        return Colleges.getByName(this.department);
    }

    // Colleges enum을 받아 department 문자열로 설정하는 메서드
    public void setDepartmentEnum(Colleges departmentEnum) {
        if (departmentEnum != null) {
            this.department = departmentEnum.getName();
        } else {
            this.department = null;
        }
    }

    // 생성자 예시 (필요에 따라 추가)
    public ManagerDto(String email, String password, String name, String department) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.department = department;
    }

    // 기타 필요한 메서드나 로직을 추가할 수 있습니다.
}
