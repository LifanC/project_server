package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Entity.W002Bean;
import com.project.server.Service.W002Service;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W002")
public class W002Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private W002Service w001Service;

    private final Gson gson = new Gson();

    @GetMapping("/goW002")
    public String goW001() {
        String goW002 = "記帳明細系統";
        logger.info("Start goW002: {}", goW002);
        return goW002;
    }

    @PostMapping("/selectData")
    public String selectData(@RequestBody W002Bean w002Bean) {
        logger.info("Start selectData: {}", w002Bean);
        return gson.toJson(w001Service.selectData(w002Bean));
    }

    @PostMapping("/selectDatax")
    public String selectDatax(@RequestBody W002Bean w002Bean) {
        logger.info("Start selectDatax: {}", w002Bean);
        return gson.toJson(w001Service.selectDatax(w002Bean));
    }

    @PostMapping("/selectDatah")
    public String selectDatah(@RequestBody W002Bean w002Bean) {
        logger.info("Start selectDatah: {}", w002Bean);
        return gson.toJson(w001Service.selectDatah(w002Bean));
    }

}
