package com.project.dudu.entities;

import com.project.dudu.enums.Colleges;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EntityListeners(value = DefaultListener.class)
@Table(name = "Student_T")
public class StudentEntity implements IEntityAdapter<LocalDateTime>{
    @Id
    @Setter
    @Getter
    private long studentId;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    @Setter
    @Getter
    private Colleges department;

    @Column(name = "create_at")
    @Setter
    @Getter
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @Setter
    @Getter
    private LocalDateTime updateAt;


    //PK 설정
    @Setter
    @Getter
    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    @Builder.Default
    private List<ReservationEntity> reservationList = new ArrayList<>();

}
