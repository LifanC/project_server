package com.project.server.Controller;

import com.project.server.Model.GoW002;
import com.project.server.Service.GoW001.W001Service;
import com.project.server.Service.GoW002.W002Service;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W002")
public class W002Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private W002Service w002Service;

    @GetMapping("/goW002")
    public String goW002(){
        logger.info("Start goW002");
        return "goW002";
    }

    @GetMapping("/goW002Show")
    public ArrayList<GoW002> goW002Show(){
        logger.info("Start goW002Show");
        return w002Service.goW002Show();
    }

    @PostMapping("/singleWord")
    public String singleWord(@RequestBody Map<String, GoW002> params) {
        GoW002 goW002 = params.get("GoW002");
        logger.info("goW002: {}", goW002);
        return w002Service.singleWord(goW002);
    }

}
