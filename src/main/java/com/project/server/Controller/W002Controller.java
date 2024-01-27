package com.project.server.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W002")
public class W002Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/goW002")
    public String goW002(){
        logger.info("Start goW002");
        return "goW002";
    }

}
