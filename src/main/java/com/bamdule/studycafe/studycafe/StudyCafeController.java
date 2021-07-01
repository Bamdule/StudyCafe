package com.bamdule.studycafe.studycafe;

import com.bamdule.studycafe.config.StudyCafeConfig;
import com.bamdule.studycafe.studycafe.service.StudyCafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/studycafe")
public class StudyCafeController {

    @Autowired
    private StudyCafeService studyCafeService;

    @Autowired
    private StudyCafeConfig studyCafeConfig;

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


}
