package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.jwt.JWTUtils;
import com.bamdule.studycafe.config.StudyCafeConfig;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.studycafe.service.StudyCafeService;
import com.bamdule.studycafe.jwt.MemberPayload;
import com.bamdule.studycafe.websocket.MessageType;
import com.bamdule.studycafe.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private SeatUsageService seatUsageService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping
    public ResponseEntity findAllStudyCafe() {
        return ResponseEntity.ok(studyCafeService.findAllStudyCafe());
    }

    @GetMapping(value = "/room")
    public ResponseEntity findAllRoom() {
        return ResponseEntity.ok(studyCafeConfig.getRooms());
    }

    @GetMapping(value = "/room/{roomId}")
    public ResponseEntity findSeatRoom(@PathVariable Integer roomId) {

        if (studyCafeConfig.getRoomMap().get(roomId) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(studyCafeService.findAllSeat(roomId));
    }

    //좌석 선택
    @PostMapping(value = "/seat/{seatId}")
    public ResponseEntity saveSeatUsage(@RequestHeader Map<String, Object> requestHeader, @PathVariable Integer seatId) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.saveSeatUsage(memberId, seatId);
        webSocketHandler.broadcast(MessageType.USE_SEAT, seatUsageVO);

        return ResponseEntity.ok(seatUsageVO);
    }

    //좌석 퇴실
    @DeleteMapping(value = "/seat")
    public ResponseEntity deleteSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.deleteSeatUsage(memberId);
        webSocketHandler.broadcast(MessageType.EXIT_SEAT, seatUsageVO);

        return ResponseEntity.ok().build();
    }

    //좌석 시간 연장
    @GetMapping(value = "/seat/availability")
    public ResponseEntity<SeatAvailability> getSeatAvailability(Integer roomId) {
        SeatAvailability seatAvailability = seatUsageService.getSeatAvailability(roomId);
        return ResponseEntity.ok(seatAvailability);
    }

    //좌석 시간 연장
    @PutMapping(value = "/seat")
    public ResponseEntity updateSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();
        SeatUsageVO seatUsageVO = studyCafeService.updateSeatUsage(memberId);
        return ResponseEntity.ok(seatUsageVO);
    }


    //내 좌석 정보
    @GetMapping(value = "/myseat")
    public ResponseEntity getMySeat(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.getSeatUsageByMemberId(memberId);
        return ResponseEntity.ok(seatUsageVO);
    }


    public MemberPayload getMemberPayload(Map<String, Object> requestHeader) {
        String accessToken = (String) requestHeader.get("authorization");
        return jwtUtils.verifyMemberJWT(accessToken);
    }

}
