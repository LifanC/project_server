package com.project.server.Controller;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexService indexService;

    private final String A = "admin";
    private final String B = "456123";

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

    @PostMapping("/permissionsFunctionSelect")
    public Map<String, ?> permissionsFunctionSelect(@RequestBody Map<String, Object> params) {
        logger.info("Start permissionsFunctionSelect: {}", params);
        String paramsAccount = MapUtils.getString(params, "account");
        String paramsPassword = MapUtils.getString(params, "password");
        Map<String, ?> map = new HashMap<>();
        if (paramsAccount.length() == 10 && paramsPassword.length() == 10) {
            map = indexService.permissionsFunctionSelect(paramsAccount, paramsPassword);
        }
        return map;
    }

    @PostMapping("/permissionsFunctionAdd")
    public boolean permissionsFunctionAdd(@RequestBody Map<String, Object> params) {
        logger.info("Start permissionsFunctionAdd: {}", params);
        String paramsAccount = MapUtils.getString(params, "account");
        String paramsPassword = MapUtils.getString(params, "password");
        if (paramsAccount.length() == 10 && paramsPassword.length() == 10) {
            return indexService.permissionsFunctionAdd(paramsAccount, paramsPassword);
        }
        return false;
    }

    @PostMapping("/permissionsFunctionAdmin")
    public Map<String, ?> permissionsFunctionAdmin(@RequestBody Map<String, Object> params) {
        logger.info("Start permissionsFunctionAdmin: {}", params);
        String paramsAccount = MapUtils.getString(params, "account");
        String paramsPassword = MapUtils.getString(params, "password");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.pathEquals(A, paramsAccount) && StringUtils.pathEquals(B, paramsPassword)) {
            map.put("isNotEmpty", true);
            map.put("selectPrivatedata", indexService.permissionsFunctionSelectAdmin());
        } else {
            map.put("isNotEmpty", false);
            map.put("selectPrivatedata", new HashMap<>());
        }
        return map;
    }
}
