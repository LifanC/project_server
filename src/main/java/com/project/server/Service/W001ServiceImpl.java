package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Entity.W001Bean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.IndexUrl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class W001ServiceImpl implements W001Service {
    @Resource
    private IndexMapper indexMapper;

    @Resource
    private W001Mapper w001Mapper;

    private String DateFormat(Date newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(formatter);
    }

    @Override
    public ResponseEntity<String> submitUpload(W001Bean w001Bean) {
        int createNum;
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
                // "金額", "種類"
                String[] field = {"money", "type"};
                for (String string : dataAll) {
                    Map<String, String> param = new HashMap<>();
                    String[] strSpt = string.split(",");
                    int count = StringUtils.countMatches(string, ",") + 1;
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
                List<IndexUrl> list = indexMapper.select(indexUrlBean);
                String accountNumber = "";
                String dataNumber = "";
                for (IndexUrl listData : list) {
                    accountNumber = listData.getAccountNumber();
                    dataNumber = listData.getDataNumber();
                }

                List<Map<String, Object>> dataParams = new ArrayList<>();
                String date = DateFormat(new Date());
                int line = 0;
                for (Map<String, String> data : params) {
                    line++;
                    String money = data.get("money");
                    if (StringUtils.isNumeric(money) && StringUtils.isBlank(money)) {
                        money = "0";
                    }
                    String type = data.get("type").trim().toUpperCase();
                    if (StringUtils.isBlank(type)) {
                        return ResponseEntity.internalServerError().body("第" + line + "行，種類(" + type + ")資料錯誤");
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("accountNumber", accountNumber);
                    param.put("dataNumber", dataNumber);
                    param.put("money", new BigDecimal(money));
                    param.put("type", type);
                    w001Bean.setDataNumber(dataNumber);
                    w001Bean.setType(type);
                    int number = Integer.parseInt(w001Mapper.maxNumber(w001Bean));
                    param.put("number", String.format("%04d", number + 1));
                    param.put("update_time", date);
                    w001Mapper.create(param);
                    dataParams.add(param);
                }
                if (dataParams.size() > 0) {
                    w001Mapper.createBatchh(dataParams);
                }
                createNum = dataParams.size();
            }
        }
        return ResponseEntity.ok().body("成功新增" + createNum + "筆");
    }

    @Override
    public ResponseEntity<String> submitForm(W001Bean w001Bean) {
        IndexUrlBean indexUrlBean = new IndexUrlBean();
        indexUrlBean.setAccountNumber(w001Bean.getAccountNumber());
        indexUrlBean.setPassword(w001Bean.getPassword());
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String dataNumber = "";
        for (IndexUrl listData : list) {
            dataNumber = listData.getDataNumber();
        }
        w001Bean.setDataNumber(dataNumber);
        String maxNumber = w001Mapper.maxNumber(w001Bean);
        int maxNumberNum = Integer.parseInt(maxNumber);
        if (Integer.parseInt(maxNumber) > 0) {
            return ResponseEntity.ok().body("已有重複資料最大編號為" + maxNumber + "有需要新增" +
                    String.format("%04d", (maxNumberNum + 1)) + "的資料嗎?");
        } else {
            return ResponseEntity.ok().body("新增編號為" +
                    String.format("%04d", (maxNumberNum + 1)) + "有需要新增嗎?");
        }
    }

    @Override
    public ResponseEntity<String> submitFormOk(W001Bean w001Bean) {
        IndexUrlBean indexUrlBean = new IndexUrlBean();
        indexUrlBean.setAccountNumber(w001Bean.getAccountNumber());
        indexUrlBean.setPassword(w001Bean.getPassword());
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }
        String date = DateFormat(new Date());
        Map<String, Object> param = new HashMap<>();
        param.put("accountNumber", accountNumber);
        param.put("dataNumber", dataNumber);
        param.put("money", w001Bean.getMoney());
        param.put("type", w001Bean.getType());
        w001Bean.setDataNumber(dataNumber);
        w001Bean.setType(w001Bean.getType());
        int number = Integer.parseInt(w001Mapper.maxNumber(w001Bean));
        param.put("number", String.format("%04d", number + 1));
        param.put("update_time", date);
        w001Mapper.create(param);
        w001Mapper.createh(param);
        return ResponseEntity.ok().body("成功新增" + 1 + "筆");
    }
}
