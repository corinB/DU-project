package com.project.dudu.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "manager")
@Entity
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;  // 관리자 이메일 (로그인용 아이디 역할)

    @Column(nullable = false)
    private String password;  // 관리자 비밀번호

    @Column(length = 100, nullable = false)
    private String name;  // 관리자 이름

    @Column(length = 100, nullable = false)
    private String university;  // 소속 대학

    @OneToMany(mappedBy = "managerEntity", orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<DepartmentEntity> departments = new ArrayList<>();  // *소속 부서 또는 관리 부서 (다대일 관계 예시)
}
