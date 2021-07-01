package com.bamdule.studycafe.studycafe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/studycafe")
public class StudyCafeController {


    @GetMapping
    public List<StudyCafeVO> findAllStudyCafe() {
        return null;
    }


}
