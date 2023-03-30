package ua.kalin.spring.conferences.controller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping({"/"})
    public String index() {
        return "common/index";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "common/contacts";
    }
}