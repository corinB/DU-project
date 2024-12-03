package com.project.dudu.entities;

import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
import com.project.dudu.enums.Colleges;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student_T")
@EntityListeners(value = DefaultListener.class)
public class StudentEntity implements IEntityAdapter<LocalDateTime> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    private String password;

    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Colleges department;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    // 관계 설정
    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    @Builder.Default
    private List<ReservationEntity> reservationList = new ArrayList<>();
}
