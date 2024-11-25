package com.project.dudu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostItemReportDto {
    private Long reportId;           // 분실물 신고 번호
    private String itemName;         // 분실물 품목
    private String category;         // 카테고리 (예: 전자기기, 의류 등)
    private LocalDateTime foundTime; // 습득 시간
    private String foundLocation;    // 습득 장소
    private String reporterName;     // 신고자 이름 (Optional)
    private LocalDateTime createAt;  // 생성 시간
    private LocalDateTime updateAt;  // 수정 시간

    // 매개변수가 있는 생성자 수정
    public LostItemReportDto(Long reportId, String itemName, String category, String foundLocation, String reporterName, LocalDateTime foundTime) {
        this.reportId = reportId;
        this.itemName = itemName;
        this.category = category;
        this.foundLocation = foundLocation;
        this.reporterName = reporterName;
        this.foundTime = foundTime;
    }
}
