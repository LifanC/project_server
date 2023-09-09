package com.project.server.Controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskController {

    @Scheduled(fixedRate = 3600000) // 每1小時執行一次
    public void runTask() {
        System.out.println("執行定時任務...");
        // 在這裡放置您的任務邏輯
    }

}
