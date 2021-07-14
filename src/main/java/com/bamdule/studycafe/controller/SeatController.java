package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.entity.seat.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/seat", produces = MediaType.APPLICATION_JSON_VALUE)
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity findAllSeatByRoomId(Integer roomId) {

        if (roomId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(seatService.findAllSeatByRoomId(roomId));
    }
}
