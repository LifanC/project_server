package com.project.server.Controller;

import com.project.server.LogUtils;
import com.project.server.Service.SetUpService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/setUp")
public class SetUpController {

    @Resource
    private SetUpService setUpService;

    @GetMapping("readCsv")
    public List<Object> readCsv() {
        List<Object> list = new ArrayList<>();
        File folder = new File("D:\\project3.0\\file_csv");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    list.add(file.getName());
                }
            }
        }
        return list;
    }

    @PostMapping("readCsvData")
    public List<Object> getCsvData(@RequestBody Map<String, String> params) {
        List<Object> list = new ArrayList<>();
        String fileName = params.get("selectCsv");
        try {
            String filePath = "D:\\project3.0\\file_csv\\" + fileName;
            List<String[]> str = setUpService.readCsvData(filePath);
            list.add(str);
            return list;
        } catch (IOException e) {
            list.add("error");
            list.add(e.getMessage());
            LogUtils.info("readCsv Error", e.getMessage());
            return list;
        }

    }
}





