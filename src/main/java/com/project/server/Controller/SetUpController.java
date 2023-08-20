package com.project.server.Controller;

import com.project.server.Model.TableName;
import com.project.server.Service.SetUpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/setUp")
public class SetUpController {

    @Resource
    private SetUpService setUpService;

    @GetMapping("/getTable")
    public List<TableName> getTable() {
        return setUpService.getTable();
    }

    @GetMapping("readCsv")
    public List<Object> readCsv() {
        List<Object> list = new ArrayList<>();

        try {
            String filePath = "D:\\project3.0\\file_csv\\fileOne.csv";
            List<String[]> str = setUpService.readCsvData(filePath);
            list.add(str);
            return list;
        } catch (IOException e) {
            list.add("error");
            list.add(String.valueOf(e));
            return list;
        }

    }

}
