package com.project.server.Controller;

import com.project.server.Model.IndexUrl;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexService indexService;

    @PostMapping("/indexUrl")
    public ArrayList<String> indexUrl(@RequestBody IndexUrl indexUrl) {
        logger.info("Start indexUrl: {}", indexUrl.getRestfulApi_type());
        String judge = "";
        switch (indexUrl.getRestfulApi_type()) {
            case "Login" -> {
                if (indexService.login(indexUrl)) {
                    judge = "true";
                } else {
                    judge = "false";
                }
            }
            case "Register" -> {
                if (indexService.register(indexUrl)) {
                    judge = "true";
                } else {
                    judge = "false";
                }
            }
            case "Delete" -> {
                if (indexService.delete(indexUrl)) {
                    judge = "true";
                } else {
                    judge = "false";
                }
            }
        }
        String type = indexUrl.getRestfulApi_type();
        logger.info("indexUrl: {}", StringUtils.pathEquals(judge,"true") ? type+" Success" : type+" Fail");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(judge);
        arrayList.add(StringUtils.pathEquals(judge,"true") ? type+" Success" : type+" Fail");
        return arrayList;
    }
}
