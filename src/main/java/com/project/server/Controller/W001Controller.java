package com.project.server.Controller;

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
    @Resource
    private W001Service w001Service;

    @GetMapping("/goW001")
    public String goW001() {
        logger.info("Start goW001");
        return "goW001";
    }

    @GetMapping("/W001UrlDefault")
    public ArrayList<Object> W001UrlDefault(
            @RequestParam String f_name,
            @RequestParam String number
    ) {
        logger.info("Start W001UrlDefault: {},{}", f_name, number);
        return w001Service.W001UrlDefault(f_name, number);
    }

    @PostMapping("/goW001Add")
    public ArrayList<Object> goW001Add(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Add: {}", goW001);
        return w001Service.goW001Add(goW001);
    }

    @PostMapping("/goW001Single_search")
    public ArrayList<Object> goW001Single_search(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        return w001Service.goW001Single_search(goW001);
    }

    @DeleteMapping("/confirmEventDelete")
    public ArrayList<Object> confirmEventDelete(@RequestParam Map<String, Object> params) {
        logger.info("Start confirmEventDelete: {}", params);
        return w001Service.confirmEventDelete(params);
    }

    @PutMapping("/goW001Modify")
    public ArrayList<Object> modify(@RequestBody Map<String, GoW001> params){
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        return w001Service.modify(goW001);
    }


}
