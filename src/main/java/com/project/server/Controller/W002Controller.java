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
    private void checking_method(GoW002Bean goW002) {
        try {
            if (goW002.getF_value() != null && goW002.getG_value() != null) {
                new BigDecimal(String.valueOf(goW002.getF_value()));
                new BigDecimal(String.valueOf(goW002.getG_value()));
            }
        } catch (NumberFormatException e) {
            logger.info("NumberFormatException: {}", e.getMessage());
        }
    }


    @PostMapping("/goW002Search")
    public boolean goW002Search(@RequestBody Map<String, GoW002Bean> params) {
        GoW002Bean goW002 = params.get("GoW002");
        logger.info("Start goW002Search: {}", goW002);
        checking_method(goW002);
        return w002Service.goW002Search(goW002);
    }

    @PostMapping("/goW002Add")
    public ArrayList<Object> goW002Add(@RequestBody Map<String, GoW002Bean> params) {
        GoW002Bean goW002 = params.get("GoW002");
        logger.info("Start goW002Add: {}", goW002);
        checking_method(goW002);
        return w002Service.goW002Add(goW002);
    }

    @DeleteMapping("/confirmEventDelete")
    public ArrayList<Object> confirmEventDelete(@RequestParam Map<String, Object> params) {
        logger.info("Start W002 confirmEventDelete: {}", params);
        return w002Service.confirmEventDelete(params);
    }

    @PutMapping("/goW002Modify")
    public ArrayList<Object> goW002Modify(@RequestBody Map<String, GoW002Bean> params) {
        GoW002Bean goW002 = params.get("GoW002");
        logger.info("Start goW002Modify: {}", goW002);
        checking_method(goW002);
        return w002Service.goW002Modify(goW002);
    }

    @PostMapping("/goW002Query")
    public ArrayList<Object> goW002Query(@RequestBody Map<String, GoW002Bean> params) {
        GoW002Bean goW002 = params.get("GoW002");
        logger.info("Start goW002Query: {}", goW002);
        checking_method(goW002);
        return w002Service.goW002Query(goW002);
    }
}
