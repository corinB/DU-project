package com.project.dudu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostItemReportDto {
    private Long id;
    private String itemName;           // 분실물
    private String description;     // 분실물 설명
    private String foundLocation;        // 분실 장소
    private String reporterName;    // 신고자 이름
    private LocalDateTime reportDate; // 신고 날짜
}
