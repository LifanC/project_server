package com.project.server.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W004")
public class W004Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/goW004")
    public String goW002() {
        logger.info("Start goW004");
        return "匯率系統";
    }

}
