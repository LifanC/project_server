package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Model.GoW001;
import com.project.server.Service.GoW001.W001Service;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson = new Gson();
    @Resource
    private W001Service w001Service;

    @GetMapping("/goW001")
    public String goW001() {
        logger.info("Start goW001");
        return "goW001";
    }

    @GetMapping("/W001UrlDefault")
    public String W001UrlDefault(
            @RequestParam String f_name,
            @RequestParam String number
    ) {
        logger.info("Start W001UrlDefault: {},{}", f_name, number);
        ArrayList<GoW001> goW001List = w001Service.W001UrlDefault(f_name, number);
        return gson.toJson(goW001List);
    }

    @PostMapping("/goW001Add")
    public String goW001Add(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Add: {}", goW001);
        ArrayList<GoW001> goW001List = w001Service.goW001Add(goW001);
        return gson.toJson(goW001List);
    }

    @PostMapping("/goW001Single_search")
    public String goW001Single_search(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        ArrayList<GoW001> goW001List = w001Service.goW001Single_search(goW001);
        return gson.toJson(goW001List);
    }

    @DeleteMapping("/confirmEventDelete")
    public String confirmEventDelete(
            @RequestParam int id,
            @RequestParam String new_date_Format,
            @RequestParam String f_name,
            @RequestParam String number
    ) {
        logger.info("Start confirmEventDelete:" +
                " id:{},new_date_Format:{},f_name:{},number:{}", id, new_date_Format, f_name, number);
        ArrayList<GoW001> goW001List = w001Service.confirmEventDelete(id, new_date_Format, f_name, number);
        return gson.toJson(goW001List);
    }

    @PutMapping("/modify")
    public String modify(@RequestBody Map<String, GoW001> params){
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        ArrayList<GoW001> goW001List = w001Service.modify(goW001);
        return gson.toJson(goW001List);
    }


}
