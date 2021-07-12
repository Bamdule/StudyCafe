package com.bamdule.studycafe.scheduler;

import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulerTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeatUsageService seatUsageService;

    //10초마다 실행
    @Scheduled(cron = "0 * * * * *")
    public void testSchedule() {

        LocalDateTime now = LocalDateTime.now();
        logger.info("[MYTEST] test batch {}", now);
        seatUsageService.deleteExpiredSeatUsages(now);

    }
}
