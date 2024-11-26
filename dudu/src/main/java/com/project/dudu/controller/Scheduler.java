package com.project.dudu.controller;

import com.project.dudu.enums.ReservationType;
import com.project.dudu.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final MessageService messageService;

    /**
     * 예약종료 알람함수
     *  - 매일 6:00, 8:00, 10:00, 12:00, 14:00, 16:00, 18:00
     **/
    @Scheduled(cron = "0 0 10-18/2 * * ?")
    @Transactional
    public void DayNoticeScheduler(){
        System.out.println("noticeScheduler");
        messageService.sendNotice(LocalDateTime.now(), ReservationType.Day);
    }


    // -------------------------------------------------------------------------------------------

    /**
     * 일일예약 종료 알림함수
     *   - 매일 19:00
     **/
    @Scheduled(cron = "0 0 19 * *")
    @Transactional
    public void DayResFinishScheduler(){
        System.out.println("finishScheduler");
        var nextDay = LocalDateTime.now().plusDays(1);
        messageService.sendFinish(nextDay, ReservationType.Day);
    }
}
