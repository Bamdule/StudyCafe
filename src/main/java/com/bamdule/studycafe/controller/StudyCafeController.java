package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.entity.reservation.ReservationVO;
import com.bamdule.studycafe.entity.reservation.service.ReservationService;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seat.service.SeatService;
import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.entity.studycafe.VO.StudyCafeVO;
import com.bamdule.studycafe.jwt.JWTUtils;
import com.bamdule.studycafe.config.StudyCafeConfig;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.studycafe.service.StudyCafeService;
import com.bamdule.studycafe.jwt.MemberPayload;
import com.bamdule.studycafe.security.AdminDetails;
import com.bamdule.studycafe.websocket.MessageType;
import com.bamdule.studycafe.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/studycafe")
public class StudyCafeController {

    @Autowired
    private StudyCafeService studyCafeService;

    @Autowired
    private StudyCafeConfig studyCafeConfig;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatUsageService seatUsageService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JWTUtils jwtUtils;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping
    public ResponseEntity<StudyCafeVO> findStudyCafe() {
        StudyCafeVO studyCafe = StudyCafeVO.builder()
                .rooms(studyCafeConfig.getRooms())
                .build();

        studyCafe.setRooms(studyCafeConfig.getRooms());

        return ResponseEntity.ok(studyCafe);
    }


    @GetMapping(value = "/room/{roomId}")
    public ResponseEntity<List<SeatVO>> findSeatsByRoomId(@PathVariable Integer roomId) {

        if (studyCafeConfig.getRoomMap().get(roomId) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(seatService.findAllSeat(roomId));
    }

    //?????? ??????
    @PostMapping(value = "/room/{roomId}/seat/{seatId}")
    public ResponseEntity saveSeatUsage(@RequestHeader Map<String, Object> requestHeader, @PathVariable Integer roomId, @PathVariable Integer seatId) {
        if (studyCafeConfig.getRoomMap().get(roomId) == null) {
            return ResponseEntity.badRequest().build();
        }

        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.saveSeatUsage(memberId, roomId, seatId);
        webSocketHandler.broadcast(MessageType.USE_SEAT, seatUsageVO);

        return ResponseEntity.ok(seatUsageVO);
    }

    //?????? ??????
    @DeleteMapping(value = "/seat")
    public ResponseEntity deleteSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.deleteSeatUsage(memberId);
        webSocketHandler.broadcast(MessageType.EXIT_SEAT, seatUsageVO);
        seatUsageService.updateSeatUsageHistoryEndDt(memberId);

        return ResponseEntity.ok().build();
    }


    //?????? ?????? ??????
    @PutMapping(value = "/seat")
    public ResponseEntity updateSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();
        SeatUsageVO seatUsageVO = studyCafeService.updateSeatUsage(memberId);
        return ResponseEntity.ok(seatUsageVO);
    }

//    //??? ?????? ??????
//    @GetMapping(value = "/myseat")
//    public ResponseEntity getMySeat(@RequestHeader Map<String, Object> requestHeader) {
//        Integer memberId = getMemberPayload(requestHeader).getMemberId();
//
//        SeatUsageVO seatUsageVO = studyCafeService.getSeatUsageByMemberId(memberId);
//
//        seatUsageService.getStudyInfo(LocalDate.now(), memberId).getStudyDays().forEach(studyDay -> logger.info("[MYTEST] studyDay : {}", studyDay));
//        ;
//        return ResponseEntity.ok(seatUsageVO);
//    }

    //??? ?????? ??????
    @GetMapping(value = "/allInfo")
    public ResponseEntity getAllInfo(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();
        return ResponseEntity.ok(studyCafeService.getAllInfo(memberId));
    }

    //??? ?????? ??????
    @GetMapping(value = "/studyInfo")
    public ResponseEntity getStudyInfo(@RequestHeader Map<String, Object> requestHeader, String studyDate) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();
        return ResponseEntity.ok(studyCafeService.getStudyInfo(memberId, studyDate));
    }

    //?????? ??????
    @GetMapping(value = "/seat/availability")
    public ResponseEntity<SeatAvailability> getSeatAvailability(Integer roomId) {
        SeatAvailability seatAvailability = seatUsageService.getSeatAvailability(roomId);
        return ResponseEntity.ok(seatAvailability);
    }

    @PostMapping(value = "/seat/reservation")
    public ResponseEntity saveReservation(@RequestHeader Map<String, Object> requestHeader) {
        MemberPayload memberPayload = getMemberPayload(requestHeader);
        ReservationVO reservationVO = reservationService.saveReservation(memberPayload.getMemberId());

        return ResponseEntity.ok(reservationVO);
    }

    @GetMapping(value = "/seat/reservation")
    public ResponseEntity getReservation() {
        return ResponseEntity.ok(reservationService.getCountReservations());
    }

    public MemberPayload getMemberPayload(Map<String, Object> requestHeader) {
        String accessToken = (String) requestHeader.get("authorization");
        return jwtUtils.verifyMemberJWT(accessToken);
    }

    private AdminDetails getAuthUserPrincipal() {
        return (AdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
