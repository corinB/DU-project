package com.project.dudu.enums;

import lombok.Getter;

@Getter
public enum Colleges {
    InformationCommunication("정보통신대"),
    Engineering("공과대"),
    Humanities("인문대"),
    social("사회대"),
    ScienceLifeConvergence("과학생명융합대"),
    Teachers("사범대"),
    Low("법행대");

    private final String name;

    Colleges(String name) {
        this.name = name;
    }
}
