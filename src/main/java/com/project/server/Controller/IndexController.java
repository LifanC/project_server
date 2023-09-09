package com.project.server.Controller;

import com.project.server.Model.A;
import com.project.server.Model.B;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    @PostMapping("/ins")
    public List<B> ins(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> mapA = new HashMap<>();
        params.forEach((key, value) -> mapA.putAll(value));
        return indexService.ins(mapA);
    }

    @PostMapping("fin")
    public List<B> fin(@RequestBody Map<String, String> params) {
        return indexService.fin(params);
    }

    @PostMapping("finA")
    public List<A> finA(@RequestBody Map<String, String> params) {
        return indexService.finA(params);
    }

    @PostMapping("find")
    public List<B> find(@RequestBody Map<String, String[]> params) {
        return indexService.find(params);
    }

    @PostMapping("findA")
    public List<A> findA(@RequestBody Map<String, List<Map<String,Object>>> params) {
        return indexService.findA(params);
    }

    @PostMapping("/del")
    public List<A> del(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return indexService.del(valuesMap);
    }

    @PostMapping("/enter")
    public List<A> enter(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return indexService.enter(valuesMap);
    }


}