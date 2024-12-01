package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Entity.W001Bean;
import com.project.server.Entity.W001TypeBean;
import com.project.server.Service.W001Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private W001Service w001Service;
    private final Gson gson = new Gson();

    @GetMapping("/goW001")
    public String goW001() {
        String goW001 = "記帳系統";
        logger.info("Start goW001: {}", goW001);
        return goW001;
    }

    @PostMapping(value = "/submitUpload", consumes = "multipart/form-data")
    public ResponseEntity<String> submitUpload(
            @Param("accountNumber") String accountNumber,
            @Param("password") String password,
            @Param("fileName") String fileName,
            @RequestParam("fileList") MultipartFile file
    ) {
        W001Bean w001Bean = W001Bean.builder()
                .accountNumber(accountNumber)
                .password(password)
                .fileName(fileName)
                .file(file)
                .build();

        logger.info("Start submitUpload: {},{},{}", accountNumber, password, fileName);
        return w001Service.submitUpload(w001Bean);
    }

    @PostMapping(value = "/submitUploadOk", consumes = "multipart/form-data")
    public ResponseEntity<String> submitUploadOk(
            @Param("accountNumber") String accountNumber,
            @Param("password") String password,
            @Param("fileName") String fileName,
            @RequestParam("fileList") MultipartFile file
    ) {
        W001Bean w001Bean = W001Bean.builder()
                .accountNumber(accountNumber)
                .password(password)
                .fileName(fileName)
                .file(file)
                .build();

        logger.info("Start submitUploadOk: {},{},{}", accountNumber, password, fileName);
        return w001Service.submitUploadOk(w001Bean);
    }

    @PostMapping("/submitForm")
    public ResponseEntity<String> submitForm(@RequestBody W001Bean w001Bean) {
        logger.info("Start submitForm: {}", w001Bean);
        return w001Service.submitForm(w001Bean);
    }

    @PostMapping("/submitFormOk")
    public ResponseEntity<String> submitFormOk(@RequestBody W001Bean w001Bean) {
        logger.info("Start submitFormOk: {}", w001Bean);
        return w001Service.submitFormOk(w001Bean);
    }

    @PostMapping("/queryForm")
    public List<Object> queryForm(@RequestBody W001Bean w001Bean) {
        logger.info("Start queryForm: {}", w001Bean);
        return w001Service.queryForm(w001Bean);
    }

    @PostMapping("/modify")
    public List<Object> modify(@RequestBody W001Bean w001Bean) {
        logger.info("Start modify: {}", w001Bean);
        return w001Service.modify(w001Bean);
    }

    @PostMapping("/eventDelete")
    public List<Object> eventDelete(@RequestBody W001Bean w001Bean) {
        logger.info("Start eventDelete: {}", w001Bean);
        return w001Service.eventDelete(w001Bean);
    }

    @GetMapping("/w001type")
    public String w001type() {
        Gson gson = new Gson();
        return gson.toJson(w001Service.w001type());
    }

    @PostMapping("/typeMethod1")
    public String typeMethod1(@RequestBody W001TypeBean w001TypeBean) {
        logger.info("Start type新增: {}", w001TypeBean);
        return gson.toJson(w001Service.typeMethod1(w001TypeBean));
    }

    @PostMapping("/typeMethod2")
    public String typeMethod2(@RequestBody W001TypeBean w001TypeBean) {
        logger.info("Start type查詢: {}", w001TypeBean);
        return gson.toJson(w001Service.typeMethod2(w001TypeBean));
    }
    @PostMapping("/eventDeleteType")
    public String eventDeleteType(@RequestBody Map<String, Object> params) {
        String typeName = params.get("typeName").toString();
        logger.info("Start type刪除: {}", typeName);
        return gson.toJson(w001Service.eventDeleteType(typeName));
    }

    @PostMapping("/typeMethod")
    public String typeMethod(@RequestBody W001TypeBean w001TypeBean) {
        logger.info("Start type查詢%: {}", w001TypeBean);
        return gson.toJson(w001Service.typeMethod(w001TypeBean));
    }

}
