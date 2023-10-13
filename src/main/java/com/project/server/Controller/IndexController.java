package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Common.Ireport;
import com.project.server.Model.B;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/index")
public class IndexController {
    private final Gson gson = new Gson();
    @Resource
    private IndexService indexService;

    @PostMapping("/ins")
    public String ins(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> mapA = new HashMap<>();
        params.forEach((key, value) -> mapA.putAll(value));
        return gson.toJson(indexService.ins(mapA));
    }

    @PostMapping("fin")
    public String fin(@RequestBody Map<String, String> params) {
        return gson.toJson(indexService.fin(params));
    }

    @PostMapping("finA")
    public String finA(@RequestBody Map<String, String> params) {
        return gson.toJson(indexService.finA(params));
    }

    @PostMapping("find")
    public String find(@RequestBody Map<String, String[]> params) {
        return gson.toJson(indexService.find(params));
    }

    @PostMapping("findA")
    public String findA(@RequestBody Map<String, List<Map<String, Object>>> params) {
        return gson.toJson(indexService.findA(params));
    }

    @PostMapping("/del")
    public String del(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return gson.toJson(indexService.del(valuesMap));
    }

    @PostMapping("/enter")
    public String enter(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return gson.toJson(indexService.enter(valuesMap));
    }

    @PostMapping("/printIreport")
    public String printIreport(@RequestBody Map<String, String[]> params) {
        if (0 != indexService.find(params).size()) {
            List<B> listB = indexService.find(params);
            List<String> list = new ArrayList<>();
            params.values().forEach(strDate -> list.addAll(Arrays.asList(strDate)));
            //列印報表
            List<Object> pdfPathList = new ArrayList<>();
            //listB值前後對調
            Collections.reverse(listB);
            String pdfPath = Ireport.reportBprintIreportPdf(listB, list.get(0), list.get(1), "");
            pdfPathList.add(pdfPath);
            pdfPathList.add(listB);
            return gson.toJson(pdfPathList);
        } else {
            return gson.toJson("err");
        }
    }

    @GetMapping("/printIreportData")
    public String printIreportData(){
        File folder = new File("D:\\project3.0\\reportBpdf\\");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String toDayYM = sdf.format(date);
        List<String> list = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            for (String s: Objects.requireNonNull(folder.list())) {
                if(toDayYM.equals(s.substring(0,6))){
                    list.add(s.substring(0,14));
                }
            }
            return gson.toJson(list);
        }else{
            return gson.toJson("");
        }
    }

    @GetMapping("/printPath")
    public String printPath(@PathParam("number") String number){
        String pdfPath = Ireport.reportBprintIreportPdf(null, "", "", number);
        return gson.toJson(pdfPath);
    }

}









