package com.project.server.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/goW001")
    public String goW001(){
        logger.info("Start goW001");
        return "goW001";
    }


}
