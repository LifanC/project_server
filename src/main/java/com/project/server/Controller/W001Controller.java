package com.project.server.Controller;

//import com.google.gson.Gson;
import com.project.server.Entity.W001Bean;
import com.project.server.Service.W001Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private W001Service w001Service;

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

}
