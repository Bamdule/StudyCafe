package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.common.JWTUtils;
import com.bamdule.studycafe.config.StudyCafeConfig;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.studycafe.service.StudyCafeService;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
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

    @PostMapping(value = "/seat/{seatId}")
    public ResponseEntity saveSeatUsage(@RequestHeader Map<String, Object> requestHeader, @PathVariable Integer seatId) {
        Integer memberId = getMemberIdByHeader(requestHeader);

        SeatUsageVO seatUsageVO = studyCafeService.saveSeatUsage(memberId, seatId);
        return ResponseEntity.ok(seatUsageVO);
    }

    @DeleteMapping(value = "/seat")
    public ResponseEntity deleteSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberIdByHeader(requestHeader);

        studyCafeService.deleteSeatUsage(memberId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/seat")
    public ResponseEntity updateSeatUsage(@RequestHeader Map<String, Object> requestHeader) {
        Integer memberId = getMemberIdByHeader(requestHeader);
        SeatUsageVO seatUsageVO = studyCafeService.updateSeatUsage(memberId);
        return ResponseEntity.ok(seatUsageVO);
    }


    public Integer getMemberIdByHeader(Map<String, Object> requestHeader) {
        String accessToken = (String) requestHeader.get("authorization");

        Map<String, Object> memberData = jwtUtils.verifyJWT(accessToken);
        if (memberData.get("memberId") == null) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER);
        } else {
            return (Integer) memberData.get("memberId");
        }
    }

}
