package com.project.server.Controller;

import com.project.server.Model.IndexUrl;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ArrayList<Object> indexUrl(@RequestBody IndexUrl indexUrl) {
        logger.info("Start indexUrl: {}", indexUrl.getRestfulApi_type());
        ArrayList<Object> arrayList = new ArrayList<>();
        boolean judge = switch (indexUrl.getRestfulApi_type()) {
            case "Login" -> indexService.login(indexUrl);
            case "Register" -> indexService.register(indexUrl);
            case "Delete" -> indexService.delete(indexUrl);
            default -> false;
        };
        String type = indexUrl.getRestfulApi_type();
        String result = judge ? type + " Success" : type + " Fail";
        logger.info("indexUrl: {}", result);
        arrayList.add(judge);
        arrayList.add(result);
        return arrayList;
    }
}
