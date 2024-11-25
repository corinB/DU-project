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
    @Scheduled(cron = "0 0 10-18/2 * * ?")
    @Transactional
    public void DayNoticeScheduler(){
        System.out.println("noticeScheduler");
        messageService.sendNotice(LocalDateTime.now(), ReservationType.Day);
    }

}
