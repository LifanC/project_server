package com.project.server.Controller;

import com.project.server.Model.GoW002;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W002")
public class W002Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/goW002")
    public String goW002(){
        logger.info("Start goW002");
        return "總務系統";
    }

    @PostMapping("/goW002Add")
    public String goW002Add(@RequestBody Map<String, GoW002> params) {
        GoW002 goW002 = params.get("GoW002");
        logger.info("Start goW002Add: {}", goW002);
        return "Test";
    }

}
