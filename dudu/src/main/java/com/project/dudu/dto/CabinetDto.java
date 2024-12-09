package com.project.dudu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dudu.enums.Colleges;
import com.project.dudu.enums.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CabinetDto {

    @JsonProperty(value = "cabinet_id")
    private Long cabinetId;

    @JsonProperty(value = "cabinet_department")
    private String department;

    @JsonProperty(value = "can_reserve")
    private boolean canReserve;

    public Colleges getCollege() {
        return Colleges.getByName(this.department);
    }

}
