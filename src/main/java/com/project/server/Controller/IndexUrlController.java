package com.project.server.Controller;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
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
}
