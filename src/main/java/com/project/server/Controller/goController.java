package com.project.server.Controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@Component
@RequestMapping("/go")
public class goController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String[] arr = {"admin", "a001", "a002", "a003", "a004", "a005"};
    private final Gson gson = new Gson();

    @PostMapping("/getGo")
    public String getGo(@RequestBody Map<String, String> params) {
        if (Arrays.asList(arr).contains(params.get("data"))) {
            return gson.toJson(params.get("data"));
        } else {
            return gson.toJson("");
        }
    }

    @GetMapping("/getUserName")
    public String getUserName() {
        return gson.toJson(arr);
    }

    @Scheduled(fixedRate = 3600000)

    public void runTask() {
        logger.info("*: {}", "執行定時任務...");
        // 在這裡放置您的任務邏輯
    }

}
