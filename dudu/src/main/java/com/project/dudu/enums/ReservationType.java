package com.project.dudu.enums;

import lombok.Getter;

@Getter
public enum ReservationType {
    Day(1, "day"), Semester(120, "semester");

    private final int value;

    private final String type;


    private ReservationType(int value, String type) {
        this.value = value;
        this.type = type;}

    static public ReservationType getByType(String type) {
        for(ReservationType reservationType : values()) {
            if(reservationType.type.equals(type)) {
                return reservationType;
            }
        }
        return null;
    }
}
