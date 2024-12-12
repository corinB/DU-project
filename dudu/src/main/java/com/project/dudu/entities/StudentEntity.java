package com.project.dudu.entities;

import com.project.dudu.dto.StudentDto;
import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
import com.project.dudu.enums.Colleges;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private long studentId;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private Colleges department;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    // 관계 설정
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReservationEntity> reservationList = new ArrayList<>();

    public StudentDto toDto() {
        return StudentDto.builder()
                .studentId(this.studentId)
                .studentName(this.studentName)
                .department(this.department.getName())
                .createAt(this.createAt)
                .updateAt(this.updateAt)
                .reservations(this.reservationList.stream()
                        .map(ReservationEntity::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
