package com.project.dudu.entities;

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
@Data
@EntityListeners(value = DefaultListener.class)
@Table(name = "Student_T")
public class StudentEntity implements IEntityAdapter<LocalDateTime>{
    @Id
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


    //PK 설정
    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    @Builder.Default
    private List<ReservationEntity> reservationList = new ArrayList<>();

}
