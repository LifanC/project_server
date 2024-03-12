package com.project.server.Controller;

import com.project.server.Service.GoW003.W003Service;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W003")
public class W003Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private W003Service w003Service;

    @GetMapping("/goW003")
    public String goW003() {
        logger.info("Start goW003");
        return "分析系統";
    }

    @PostMapping("/goW003monthProportion")
    public ArrayList<Object> goW003monthProportion(@RequestBody Map<String, String[]> params) {
        String[] fNume_number_array = params.getOrDefault("GoW003_fNume_number", new String[0]);
        logger.info("Start goW003_fNume_numberLog: {}", String.join(",", fNume_number_array));
        String[] setDateRangeLast_array = params.getOrDefault("GoW003_setDateRangeLast", new String[0]);
        String[] setDateRangeThis_array = params.getOrDefault("GoW003_setDateRangeThis", new String[0]);
        String[] setDateRangeNext_array = params.getOrDefault("GoW003_setDateRangeNext", new String[0]);
        logger.info("Start GoW003_setDateRangeLast: {}", String.join(",", setDateRangeLast_array));
        logger.info("Start GoW003_setDateRangeThis: {}", String.join(",", setDateRangeThis_array));
        logger.info("Start GoW003_setDateRangeNext: {}", String.join(",", setDateRangeNext_array));
        return w003Service.goW003monthProportion(fNume_number_array, setDateRangeLast_array, setDateRangeThis_array, setDateRangeNext_array);
    }

}
