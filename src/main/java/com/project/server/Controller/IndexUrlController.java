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
        ArrayList<String> arrayList = new ArrayList<>();
        String judge = switch (indexUrl.getRestfulApi_type()) {
            case "Login" -> indexService.login(indexUrl) ? "true" : "false";
            case "Register" -> indexService.register(indexUrl) ? "true" : "false";
            case "Delete" -> indexService.delete(indexUrl) ? "true" : "false";
            default -> "";
        };
        String type = indexUrl.getRestfulApi_type();
        String result = StringUtils.pathEquals(judge, "true") ? type + " Success" : type + " Fail";
        logger.info("indexUrl: {}", result);
        arrayList.add(judge);
        arrayList.add(result);
        return arrayList;
    }
}
