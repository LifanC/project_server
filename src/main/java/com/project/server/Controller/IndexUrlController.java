package com.project.server.Controller;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Model.IndexUrl;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexService indexService;
    private final static String account = "admin";
    private final static String password = "456123";

    @GetMapping("/indexUrlDefault")
    public List<Map<String, Object>> indexUrlDefault() {
        logger.info("Start indexUrlDefault");
        return indexService.indexUrlDefault();
    }

    @PostMapping("/indexUrl")
    public List<Object> indexUrl(@RequestBody IndexUrlBean indexUrlBean) {
        logger.info("Start indexUrl: {}", indexUrlBean);
        String type = indexUrlBean.getRestfulApi_type();
        boolean[] judge = switch (type) {
            case "Login" -> indexService.login(indexUrlBean);
            case "Register" -> indexService.register(indexUrlBean);
            case "Delete" -> indexService.delete(indexUrlBean);
            default -> new boolean[]{false, false};
        };

        List<Object> result = new ArrayList<>();
        result.add(judge[1]);
        if (judge[0]) {
            if (judge[1]) {
                result.add(type + " Success");
            } else {
                result.add(type + " Fail，重複註冊");
            }
        } else {
            result.add(type + " Fail");
        }
        logger.info("indexUrl: {}", result);
        return result;
    }

    @PostMapping("/indexUrlPermissions")
    public boolean indexUrlPermissions(@RequestBody IndexUrlBean indexUrlBean) {
        logger.info("Start indexUrlPermissions: {}", indexUrlBean);
        return indexService.indexUrlPermissions(indexUrlBean).size() > 0;
    }

    @GetMapping("/permissionsDefault")
    public String[] permissionsDefault() {
        logger.info("Start permissionsDefault");
        return new String[]{account, password};
    }

    @PostMapping("/permissionsFunction")
    public boolean permissionsFunction(@RequestBody Map<String, Object> params) {
        logger.info("Start permissionsFunction: {}", params);
        String params_account = MapUtils.getString(params, "account").toLowerCase();
        String params_password = MapUtils.getString(params, "password").toLowerCase();
        return StringUtils.equals(account, params_account)
                && StringUtils.equals(password, params_password);
    }
}
