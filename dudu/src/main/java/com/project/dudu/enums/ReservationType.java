package com.project.dudu.enums;

import lombok.Getter;

@Getter
public enum ReservationType {
    Day(1), Semester(120);

    private final int value;

    private ReservationType(int value) {
        this.value = value;
    }

}
