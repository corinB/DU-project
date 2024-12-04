package com.project.dudu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstPageController {

    @GetMapping("/dureservation")
    public String showMainPage() {
        return "mainpage";
    }
}
