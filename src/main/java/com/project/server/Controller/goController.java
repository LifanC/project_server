package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.LogUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@Component
@RequestMapping("/go")
public class goController {

    @PostMapping("/getGo")
    public String getGo(@RequestBody Map<String,String> params) {
        Gson gson = new Gson();
        String[] arr = {"admin"};
        if (Arrays.asList(arr).contains(params.get("data"))) {
            return gson.toJson(params.get("data"));
        } else {
            return "";
        }
    }

    @Scheduled(fixedRate = 3600000)
    @GetMapping("/runTask")
    public void runTask() {
        LogUtils.info("*","執行定時任務...");
        // 在這裡放置您的任務邏輯
    }

}
