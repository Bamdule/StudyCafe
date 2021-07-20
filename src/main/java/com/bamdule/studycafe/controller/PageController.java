package com.bamdule.studycafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/studycafe")
public class PageController {

    @GetMapping(value = "")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("pages/seats");
        return mav;
    }

//    @GetMapping(value = "/seatMaker/login")
//    public String seatMakerLogin() {
//        return "";
//    }
//
//    @GetMapping(value = "/seatMaker/login")
//    public String seatMakerLogin() {
//        return "";
//    }

}
