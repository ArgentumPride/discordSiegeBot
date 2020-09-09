package ua.pride.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @GetMapping("/hello")
    public String message() {
         return "hello";
    }

    @GetMapping("/heroku")
    public String heroku() {
        return "heroku";
    }

}
