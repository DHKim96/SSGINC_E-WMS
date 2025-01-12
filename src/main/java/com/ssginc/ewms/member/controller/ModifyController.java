package com.ssginc.ewms.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("modify")
@RequiredArgsConstructor
public class ModifyController {

    @GetMapping("")
    public String verify() {
        return "member/verify";
    }
}
