package com.project.dudu.enums;

import lombok.Getter;

@Getter
public enum ReservationType {
    Day(1, "Day", "19"),
    Semester(120, "Semester", "2024-12-16 16:00:00");

    private final int value; // 예약 타입 값 (날짜 추가 기준)
    private final String type; // 예약 타입 이름
    private final String date; // 예약 기본 날짜 문자열

    private ReservationType(int value, String type, String date) {
        this.value = value;
        this.type = type; // 올바르게 할당
        this.date = date; // 올바르게 할당
    }

    public static ReservationType getByType(String type) {
        for (ReservationType reservationType : values()) {
            if (reservationType.type.equals(type)) {
                return reservationType;
            }
        }
        return null;
    }
}
