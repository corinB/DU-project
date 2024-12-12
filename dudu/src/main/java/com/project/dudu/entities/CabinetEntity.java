package com.project.dudu.entities;

import com.project.dudu.dto.CabinetDto;
import com.project.dudu.entities.util.DefaultListener;
import com.project.dudu.entities.util.IEntityAdapter;
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
@Table(name = "Cabinet_T")
public class CabinetEntity implements IEntityAdapter<LocalDateTime> {
    @Id
    @Column(name = "cabinet_id")
    private Long cabinetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cabinet_department")
    private Colleges department;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    //PK 설정
    @OneToMany(mappedBy = "cabinet")
    @ToString.Exclude
    @Builder.Default
    private List<ReservationEntity> reservationList = new ArrayList<>();

    public Boolean canReserve() {
        return reservationList.stream()
                .allMatch(ReservationEntity::isDone);
    }


    public CabinetDto toDto() {
        return CabinetDto.builder()
                .cabinetId(this.cabinetId)
                .department(this.department.getName())
                .canReserve(this.canReserve())
                .build();
    }
}
