package com.project.server.Controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/go")
public class goController {

    @PostMapping("/getGo")
    public String getGo(@RequestBody Map<String,String> params) {
        Gson gson = new Gson();
        String[] arr = {"Luke", "admin"};
        if (Arrays.asList(arr).contains(params.get("data"))) {
            return gson.toJson(params.get("data"));
        } else {
            return "";
        }
    }

}
