package com.project.server.Controller;

import com.project.server.Entity.GoW002Bean;
import com.project.server.Service.GoW002.W002Service;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public String goW002() {
        logger.info("Start goW002");
        return "總務系統";
    }

    @GetMapping("/W002UrlDefault")
    public ArrayList<Object> W002UrlDefault(
            @RequestParam String f_name,
            @RequestParam String number
    ) {
        logger.info("Start W002UrlDefault: {},{}", f_name, number);
        return w002Service.W002UrlDefault(f_name, number);
    }

    @PostMapping("/goW002Add")
    public ArrayList<Object> goW002Add(@RequestBody Map<String, GoW002Bean> params) {
        GoW002Bean goW002 = params.get("GoW002");
        logger.info("Start goW002Add: {}", goW002);
        String[] value_strings = {
                String.valueOf(goW002.getF_value()),
                String.valueOf(goW002.getG_value())
        };
        try {
            for (String strings : value_strings) {
                new BigDecimal(strings);
            }
        } catch (NumberFormatException e) {
            logger.info("NumberFormatException: {}", e.getMessage());
            return null;
        }
        return w002Service.goW002Add(goW002);
    }

}