package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Entity.W001Bean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class W001ServiceImpl implements W001Service {
    @Resource
    private IndexMapper indexMapper;

//    @Resource
//    private W001Mapper w001Mapper;

    private String DateFormat(Date newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(formatter);
    }

    @Override
    public ResponseEntity<String> submitUpload(W001Bean w001Bean) {
        if (w001Bean.getFileName().contains(".")) {
            return ResponseEntity.internalServerError().body("檔案有誤");
        } else {
            List<String> dataAll = new ArrayList<>();
            BufferedReader reader = null;
            try {
                InputStream inputStream = w001Bean.getFile().getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    dataAll.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(reader);
            }
            if (dataAll.isEmpty()) {
                return ResponseEntity.internalServerError().body("無資料");
            } else if (dataAll.size() > 10000) {
                return ResponseEntity.internalServerError().body("超過10000筆資料");
            } else {
                List<Map<String, String>> params = new ArrayList<>();
                // "金額", "種類", "編號", "檢查"
                String[] field = {"money", "type", "number", "check"};
                for (String string : dataAll) {
                    Map<String, String> param = new HashMap<>();
                    String[] strSpt = string.split(",");
                    int count = StringUtils.countOccurrencesOf(string, ",") + 1;
                    if (count == field.length) {
                        for (int i = 0; i < strSpt.length; i++) {
                            param.put(field[i], strSpt[i].trim());
                        }
                    } else {
                        return ResponseEntity.internalServerError().body("資料錯誤");
                    }
                    params.add(param);
                }

                IndexUrlBean indexUrlBean = IndexUrlBean.builder()
                        .accountNumber(w001Bean.getAccountNumber())
                        .password(w001Bean.getPassword())
                        .build();
                List<IndexUrl>  list = indexMapper.select(indexUrlBean);
                String dataNumber = "";
                for (IndexUrl listData : list) {
                    dataNumber = listData.getDataNumber();
                }
                for (Map<String, String> data : params) {
                    data.put("dataNumber", dataNumber);
                    data.put("fileName", w001Bean.getFileName());
                    data.put("updateDate", DateFormat(new Date()));
                    System.out.println(data);
                }
            }
        }
        return ResponseEntity.ok().body("成功");
    }
}
