package com.project.server.Controller;

import com.project.server.Model.IndexUrl;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexService indexService;

    @PostMapping("/indexUrl")
    public boolean indexUrl(@RequestBody IndexUrl indexUrl){
        logger.info("Start indexUrl");
        return indexService.insert(indexUrl);
    }

}
