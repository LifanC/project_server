package com.project.server.Controller;

import com.project.server.LogUtils;
import com.project.server.Service.SetUpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        LogUtils.info("讀CSV檔名稱", "*************** readCsv Start ***************");
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
        LogUtils.info("讀CSV檔", "*************** readCsvData Start ***************");
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

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        LogUtils.info("上傳CSV檔", "*************** fileUpload Start ***************");
        try {
            // 指定存儲路徑
            String uploadPath = "D:\\project3.0\\file_csv\\";
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + fileName;
            // 檢查檔案是否已經存在
            File targetFile = new File(filePath);
            if (targetFile.exists()) {
                return "檔案已存在";
            }
            // 將文件寫入指定路徑
            file.transferTo(new File(filePath));
            return "文件上傳成功";
        } catch (IOException e) {
            return "文件上傳失敗：" + e.getMessage();
        }
    }
}





