package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.entity.reservation.ReservationVO;
import com.bamdule.studycafe.entity.reservation.service.ReservationService;
import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;
import com.bamdule.studycafe.jwt.JWTUtils;
import com.bamdule.studycafe.config.StudyCafeConfig;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.studycafe.service.StudyCafeService;
import com.bamdule.studycafe.jwt.MemberPayload;
import com.bamdule.studycafe.security.AdminDetails;
import com.bamdule.studycafe.websocket.MessageType;
import com.bamdule.studycafe.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    private SeatUsageService seatUsageService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JWTUtils jwtUtils;

//    @GetMapping
//    public ResponseEntity findAllStudyCafe() {
//        return ResponseEntity.ok(studyCafeService.findAllStudyCafe());
//    }

    @GetMapping(value = "")
    public ResponseEntity<StudyCafeVO> findStudyCafe() {
        AdminDetails adminDetails = getAuthUserPrincipal();
        StudyCafeVO studyCafe = adminDetails.getStudyCafe();

        studyCafe.setRooms(studyCafeConfig.getRooms());

        return ResponseEntity.ok(studyCafe);
    }


    @GetMapping(value = "/room/{roomId}")
    public ResponseEntity<List<SeatVO>> findSeatsByRoomId(@PathVariable Integer roomId) {

        if (studyCafeConfig.getRoomMap().get(roomId) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(studyCafeService.findAllSeat(roomId));
    }

    //좌석 선택
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

    //좌석 퇴실
    @DeleteMapping(value = "/seat")
    public ResponseEntity deleteSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberPayload(requestHeader).getMemberId();

        SeatUsageVO seatUsageVO = studyCafeService.deleteSeatUsage(memberId);
        webSocketHandler.broadcast(MessageType.EXIT_SEAT, seatUsageVO);

        return ResponseEntity.ok().build();
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

    //좌석 현황
    @GetMapping(value = "/seat/availability")
    public ResponseEntity<SeatAvailability> getSeatAvailability(Integer roomId) {
        SeatAvailability seatAvailability = seatUsageService.getSeatAvailability(roomId);
        return ResponseEntity.ok(seatAvailability);
    }

    @PostMapping(value = "/seat/reservation")
    public ResponseEntity saveReservation(@RequestHeader Map<String, Object> requestHeader) {
        AdminDetails adminDetails = getAuthUserPrincipal();
        MemberPayload memberPayload = getMemberPayload(requestHeader);
        ReservationVO reservationVO = reservationService.saveReservation(adminDetails.getStudyCafe().getId(), memberPayload.getMemberId());

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
