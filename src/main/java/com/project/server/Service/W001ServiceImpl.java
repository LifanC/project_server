package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Entity.W001Bean;
import com.project.server.Entity.W001TypeBean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.IndexUrl;
import com.project.server.Model.W001;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ArrayUtils;
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
        return newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
    }

    private List<String> w001StreamMethod(W001Bean w001Bean) {
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
        return dataAll;
    }

    private boolean w001Map(List<String> dataAll, List<Map<String, String>> params) {
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
                return true;
            }
            params.add(param);
        }
        return false;
    }

    @Override
    public ResponseEntity<String> submitUpload(W001Bean w001Bean) {
        List<List<String>> listLogs = new ArrayList<>();
        String accountNumber = "";
        String dataNumber = "";
        Set<String> types = new HashSet<>();

        System.out.println(!w001Bean.getFileName().contains(".csv"));
        if (!w001Bean.getFileName().contains(".csv")) {
            return ResponseEntity.internalServerError().body("非CSV檔，請重新上傳");
        } else {
            List<String> dataAll = w001StreamMethod(w001Bean);
            if (dataAll.isEmpty()) {
                return ResponseEntity.internalServerError().body("無資料");
            } else if (dataAll.size() > 10000) {
                return ResponseEntity.internalServerError().body("超過10000筆資料");
            } else {
                List<Map<String, String>> params = new ArrayList<>();
                boolean w001Map = w001Map(dataAll, params);
                if (w001Map) {
                    ResponseEntity.internalServerError().body("資料錯誤");
                }
                IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w001Bean.getAccountNumber()).password(w001Bean.getPassword()).build();
                List<IndexUrl> list = indexMapper.select(indexUrlBean);
                for (IndexUrl listData : list) {
                    accountNumber = listData.getAccountNumber();
                    dataNumber = listData.getDataNumber();
                }
                List<Map<String, String>> letters = w001Mapper.typeSelectList();
                System.out.println(letters);
                String[] letter = new String[letters.size()];
                for (int i = 0; i < letters.size(); i++) {
                    letter[i] = letters.get(i).get("typeNameNumber");
                }
                int line = 0;
                for (Map<String, String> data : params) {
                    line++;
                    types.add(data.get("type"));
                    getLogMessage(data, listLogs, line, letter);
                }
            }
        }
        if (listLogs.isEmpty()) {
            w001Bean.setDataNumber(dataNumber);
            List<List<String>> listLogOks = new ArrayList<>();
            for (String type : types) {
                List<String> listLogOk = new ArrayList<>();
                w001Bean.setType(type);
                String maxNumber = w001Mapper.maxNumber(w001Bean);
                int maxNumberNum = Integer.parseInt(maxNumber);
                String log = "";
                if (Integer.parseInt(maxNumber) <= 0) {
                    log = "編號為" + String.format("%07d", (maxNumberNum + 1)) + "有需要新增嗎?";
                } else {
                    log = "已有重複資料最大編號為" + maxNumber + "<br>有需要新增" + String.format("%07d", (maxNumberNum + 1)) + "的資料嗎?";
                }
                listLogOk.add("種類" + type + ":" + log + "<br>");
                listLogOks.add(listLogOk);
            }
            return ResponseEntity.ok().body(listLogOks.toString());
        } else {
            return ResponseEntity.internalServerError().body(listLogs.toString());
        }
    }

    @Override
    public ResponseEntity<String> submitUploadOk(W001Bean w001Bean) {
        List<String> dataAll = w001StreamMethod(w001Bean);
        List<Map<String, String>> params = new ArrayList<>();
        boolean w001Map = w001Map(dataAll, params);
        if (w001Map) {
            ResponseEntity.internalServerError().body("資料錯誤");
        }
        List<Map<String, Object>> dataParams = new ArrayList<>();
        IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w001Bean.getAccountNumber()).password(w001Bean.getPassword()).build();
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }
        String date = DateFormat(new Date());
        int createNum = 0;
        for (Map<String, String> paramString : params) {
            Map<String, Object> param = new HashMap<>();
            param.put("accountNumber", accountNumber);
            param.put("dataNumber", dataNumber);
            param.put("money", new BigDecimal(paramString.get("money")));
            String type = paramString.get("type");
            param.put("type", type);
            w001Bean.setDataNumber(dataNumber);
            w001Bean.setType(type);
            int number = Integer.parseInt(w001Mapper.maxNumber(w001Bean));
            param.put("number", String.format("%07d", number + 1));
            param.put("update_date", date);
            param.put("update_time", date);
            int create = w001Mapper.create(param);
            createNum += create;
            param.put("update_cd", "新增");
            dataParams.add(param);
        }
        w001Mapper.createBatchh(dataParams);
        return ResponseEntity.ok().body("成功新增" + createNum + "筆");
    }

    private void getLogMessage(Map<String, String> data, List<List<String>> listLogs, int line, String[] letter) {
        List<String> listLog = new ArrayList<>();
        String log = "第" + line + "行";
        boolean moneyIsNotOk = true;
        boolean typeIsNotOk = true;

        String money = data.get("money");
        if (StringUtils.isNotBlank(money)) {
            if (!StringUtils.isNumeric(money)) {
                log += "。金額(" + money + ")格式錯誤";
            } else if (Integer.parseInt(money) < 1) {
                log += "。金額(" + money + ")勿小於 1";
            } else {
                moneyIsNotOk = false;
            }
        } else {
            log += "。金額勿空白";
        }
        String type = data.get("type");
        if (StringUtils.isNotBlank(type)) {
            if (!ArrayUtils.contains(letter, type)) {
                log += "、種類(" + type + ")格式錯誤";
            } else {
                typeIsNotOk = false;
            }
        } else {
            log += "、種類勿空白";
        }
        if (moneyIsNotOk || typeIsNotOk) {
            listLog.add(log);
            listLogs.add(listLog);
        }
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
            return ResponseEntity.ok().body("已有重複資料最大編號為" + maxNumber + "有需要新增" + String.format("%07d", (maxNumberNum + 1)) + "的資料嗎?");
        } else {
            return ResponseEntity.ok().body("新增編號為" + String.format("%07d", (maxNumberNum + 1)) + "有需要新增嗎?");
        }
    }

    @Override
    public ResponseEntity<String> submitFormOk(W001Bean w001Bean) {
        IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w001Bean.getAccountNumber()).password(w001Bean.getPassword()).build();
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }
        String updateDate = DateFormat(w001Bean.getUpdate_date());
        String date = DateFormat(new Date());
        Map<String, Object> param = new HashMap<>();
        param.put("accountNumber", accountNumber);
        param.put("dataNumber", dataNumber);
        param.put("money", w001Bean.getMoney());
        param.put("type", w001Bean.getType());
        w001Bean.setDataNumber(dataNumber);
        w001Bean.setType(w001Bean.getType());
        int number = Integer.parseInt(w001Mapper.maxNumber(w001Bean));
        param.put("number", String.format("%07d", number + 1));
        param.put("update_date", updateDate);
        param.put("update_time", date);
        int create = w001Mapper.create(param);
        param.put("update_cd", "新增");
        w001Mapper.createh(param);
        return ResponseEntity.ok().body("成功新增" + create + "筆");
    }

    @Override
    public List<Object> queryForm(W001Bean w001Bean) {
        if (w001Bean.getUpdate_date() != null) {
            String updateDate = DateFormat(w001Bean.getUpdate_date());
            w001Bean.setUpdate_date_format(updateDate);
        }
        List<W001> select = w001Mapper.select(w001Bean);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }

    @Override
    public List<Object> modify(W001Bean w001Bean) {
        String date = DateFormat(new Date());
        IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w001Bean.getAccountNumber()).password(w001Bean.getPassword()).build();
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }
        Map<String, Object> param = new HashMap<>();
        param.put("money", w001Bean.getMoney());
        param.put("update_time", date);
        param.put("accountNumber", accountNumber);
        param.put("dataNumber", dataNumber);
        param.put("number", w001Bean.getNumber());
        param.put("type", w001Bean.getType());
        w001Mapper.update(param);
        param.put("update_cd", "修改");
        w001Mapper.createh(param);
        List<W001> select = w001Mapper.select(w001Bean);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }

    @Override
    public List<Object> eventDelete(W001Bean w001Bean) {
        String date = DateFormat(new Date());
        IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w001Bean.getAccountNumber()).password(w001Bean.getPassword()).build();
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }
        Map<String, Object> param = new HashMap<>();
        param.put("money", w001Bean.getMoney());
        param.put("update_time", date);
        param.put("accountNumber", accountNumber);
        param.put("dataNumber", dataNumber);
        param.put("number", w001Bean.getNumber());
        param.put("type", w001Bean.getType());
        w001Mapper.delete(param);
        param.put("update_cd", "刪除");
        w001Mapper.createh(param);
        List<W001> select = w001Mapper.select(w001Bean);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }

    @Override
    public List<Map<String, String>> w001type() {
        return w001Mapper.typeSelectList();
    }

    @Override
    public List<Object> typeMethod1(W001TypeBean w001TypeBean) {
        List<Object> result = new ArrayList<>();
        List<Map<String, String>> typeList = w001Mapper.typeSelectList();
        if (typeList.size() == 0) {
            w001Mapper.insertType(w001TypeBean.getTypeName(), "1");
        } else {
            List<Map<String, String>> typeSelectTypeName = w001Mapper.w001typeSelect(w001TypeBean.getTypeName());
            if (typeSelectTypeName.size() == 0) {
                int count = 0;
                for (int i = 0; i < typeList.size(); i++) {
                    if (Integer.parseInt(typeList.get(i).get("typeNameNumber")) != i + 1) {
                        w001Mapper.insertType(w001TypeBean.getTypeName(), String.valueOf(i + 1));
                        break;
                    }
                    count++;
                }
                if (count == typeList.size()) {
                    w001Mapper.insertType(w001TypeBean.getTypeName(), String.valueOf(typeList.size() + 1));
                }
            }
        }
        List<Map<String, String>> typeSelectTypeName = w001Mapper.typeSelectList();
        result.add(typeSelectTypeName);
        return result;
    }

    @Override
    public List<Object> typeMethod2(W001TypeBean w001TypeBean) {
        List<Map<String, String>> typeSelectTypeName = w001Mapper.w001typeSelect(w001TypeBean.getTypeName());
        List<Object> result = new ArrayList<>();
        result.add((typeSelectTypeName.size() > 0) ? typeSelectTypeName : new ArrayList<>());
        return result;
    }

    @Override
    public List<Object> eventDeleteType(String typeName) {
        List<Object> result = new ArrayList<>();
        int del = w001Mapper.deleteType(typeName);
        if (del > 0) {
            List<Map<String, String>> typeSelects = w001Mapper.typeSelect(typeName);
            typeSelects.forEach(typeSelect -> {
                String typeNameNumber = typeSelect.get("typeNameNumber").toString();
                w001Mapper.deleteW001(typeNameNumber);
                w001Mapper.deleteW001h(typeNameNumber);
            });
        }
        List<Map<String, String>> typeSelectTypeName = w001Mapper.typeSelectList();
        result.add(typeSelectTypeName);
        return result;
    }

    @Override
    public List<Object> typeMethod(W001TypeBean w001TypeBean) {
        List<Object> result = new ArrayList<>();
        List<Map<String, String>> typeList = w001Mapper.typeSelectList();
        result.add(typeList);
        return result;
    }
}
