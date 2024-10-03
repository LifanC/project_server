package com.project.server.Controller;

//import com.google.gson.Gson;

import com.project.server.Entity.GoW005Bean;
import com.project.server.Service.GoW005.W005Service;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W005")
public class W005Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //    private final Gson gson = new Gson();
    private final static String path =
            "D:" + File.separator + "project3.0" + File.separator + "W005";

    @Resource
    private W005Service w005Service;

    @GetMapping("/goW005")
    public String goW005() {
        logger.info("Start goW005");
        return "讀取資料";
    }

    @GetMapping("/W005UrlDefault")
    public String W005UrlDefault(
            @RequestParam String f_name,
            @RequestParam String number,
            @RequestParam String fileName
    ) {
        logger.info("Start W005UrlDefault: {},{},{}", f_name, number, fileName);
        if (StringUtils.isBlank(f_name) && StringUtils.isBlank(number)) {
            return "";
        }
        return path + File.separator + fileName;
    }

    @PostMapping("/goW005methodSelect")
    public Map<String, Object> goW005methodSelect(@RequestBody Map<String, GoW005Bean> params) {
        GoW005Bean goW005 = params.get("GoW005");
        logger.info("Start goW005: {}", goW005);
        if (StringUtils.isBlank(goW005.getF_name()) && StringUtils.isBlank(goW005.getNumber())) {
            return null;
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("data", w005Service.goW005methodSelect(goW005.getFileName(), path));
        return returnMap;
    }

}
