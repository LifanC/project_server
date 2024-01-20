package com.project.server.Controller;

import com.project.server.Model.IndexUrl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {

    @PostMapping("/indexUrl")
    public boolean indexUrl(@RequestBody IndexUrl indexUrl){
        return "L".equals(indexUrl.getF_name()) && "07".equals(indexUrl.getNumber());
    }

}
