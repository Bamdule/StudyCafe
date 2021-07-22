package com.bamdule.studycafe.scheduler;

import com.bamdule.studycafe.config.PropertyConfig;
import com.bamdule.studycafe.entity.reservation.service.ReservationService;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.websocket.MessageType;
import com.bamdule.studycafe.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SchedulerTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeatUsageService seatUsageService;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private ReservationService reservationService;

    //1분 마다 실행
    @Scheduled(cron = "0 * * * * *")
    public void checkExpiredSeatSchedule() {

        if (propertyConfig.isUseSeatSchedule()) {
            LocalDateTime now = LocalDateTime.now();
            logger.info("[MYTEST] test batch {}", now);
            List<SeatUsageVO> expiredSeatUsages = seatUsageService.getExpiredSeatUsages(now);
            if (expiredSeatUsages.size() > 0) {
                seatUsageService.deleteExpiredSeatUsages(now);
                webSocketHandler.broadcast(MessageType.EXPIRED_SEAT, expiredSeatUsages);
            }

            reservationService.executeReservation();
        }

    }
}
