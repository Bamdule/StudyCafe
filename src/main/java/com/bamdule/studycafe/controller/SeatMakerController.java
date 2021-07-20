package com.bamdule.studycafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/seatMaker")
public class SeatMakerController {
    @GetMapping(value = "/login")
    public String seatMakerLogin() {
        return "";
    }

    @GetMapping(value = "")
    public String seatMaker() {
        return "";
    }
}
