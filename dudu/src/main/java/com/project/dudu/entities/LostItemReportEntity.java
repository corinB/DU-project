package com.project.dudu.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Lost_item_report_T")
@EntityListeners(value = DefaultListener.class)
@Entity
public class LostItemReportEntity implements IEntityAdapter<LocalDateTime>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;  // 분실물 신고 번호

    @Column(length = 100, nullable = false)
    private String itemName;  // 분실물 품목

    @Column(length = 50, nullable = false)
    private String category;  // 카테고리 (예: 전자기기, 의류 등)

    @Column(nullable = false)
    private LocalDateTime foundTime;  // 습득 시간

    @Column(length = 200, nullable = false)
    private String foundLocation;  // 습득 장소

    @Column(length = 100, nullable = true)
    private String foundPerson;  //습득인

    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
