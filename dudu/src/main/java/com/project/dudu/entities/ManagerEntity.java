package com.project.dudu.entities;

import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
import com.project.dudu.enums.Colleges;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = DefaultListener.class)
@Data
@Table(name = "Manager_T")
@Entity
public class ManagerEntity implements IEntityAdapter<LocalDateTime> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;  // 관리자 이메일 (로그인용 아이디 역할)

    @Column(nullable = false)
    private String password;  // 관리자 비밀번호

    @Column(length = 100, nullable = false)
    private String name;  // 관리자 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Colleges department;  // 소속 대학 (ENUM)

    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
