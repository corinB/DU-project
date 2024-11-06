package com.project.dudu.enums;

import lombok.Getter;

@Getter
public enum Colleges {
    InformationCommunication("정보통신대학"),
    Engineering("공과대학"),
    Humanities("인문대학"),
    social("사회과학대학"),
    ScienceLifeConvergence("과학생명융합대학"),
    Teachers("사범대학"),
    Low("법_행정대학"),
    business("경영대학"),
    ArtAndDesign("조형예술대학"),
    RehabilitationSciences("재활과학대학"),
    Nursing("간호대학");

    private final String name;

    Colleges(String name) {
        this.name = name;
    }

    static public Colleges getByName(String name){
        for(Colleges college : values()){
            if(college.name.equals(name)){
                return college;
            }
        }
        return null;
    }
}
