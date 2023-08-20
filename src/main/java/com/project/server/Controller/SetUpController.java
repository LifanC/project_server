package com.project.server.Controller;

import com.project.server.Model.TableName;
import com.project.server.Service.SetUpService;
import jakarta.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
    public List<String[]> readCsv() {

        try {
            String filePath = "D:\\project3.0\\file_csv\\fileOne.csv";
            return setUpService.readCsvData(filePath);
        } catch (IOException e) {
            return null;
        }

    }

}
