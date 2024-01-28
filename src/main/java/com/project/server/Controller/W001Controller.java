package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Common.Ireport;
import com.project.server.Model.GoW001;
import com.project.server.Service.GoW001.W001Service;
import jakarta.annotation.Resource;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String rootDirectory = "D:\\project3.0\\";
    private final Gson gson = new Gson();
    @Resource
    private W001Service w001Service;

    @GetMapping("/goW001")
    public String goW001() {
        logger.info("Start goW001");
        return "goW001";
    }

    @GetMapping("/W001UrlDefault")
    public ArrayList<Object> W001UrlDefault(
            @RequestParam String f_name,
            @RequestParam String number
    ) {
        logger.info("Start W001UrlDefault: {},{}", f_name, number);
        return w001Service.W001UrlDefault(f_name, number);
    }

    @PostMapping("/goW001Add")
    public ArrayList<Object> goW001Add(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Add: {}", goW001);
        return w001Service.goW001Add(goW001);
    }

    @PostMapping("/goW001Single_search")
    public ArrayList<Object> goW001Single_search(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        return w001Service.goW001Single_search(goW001);
    }

    @DeleteMapping("/confirmEventDelete")
    public ArrayList<Object> confirmEventDelete(@RequestParam Map<String, Object> params) {
        logger.info("Start confirmEventDelete: {}", params);
        return w001Service.confirmEventDelete(params);
    }

    @PutMapping("/goW001Modify")
    public ArrayList<Object> goW001Modify(@RequestBody Map<String, GoW001> params) {
        GoW001 goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        return w001Service.goW001Modify(goW001);
    }

    @PostMapping("/goW001Search")
    public ArrayList<Object> goW001Search(@RequestBody Map<String, String[]> params) {
        String[] goW001_datePickers_array = params.get("GoW001_datePicker");
        logger.info("Start goW001Search: {},{}", goW001_datePickers_array[0], goW001_datePickers_array[1]);
        return w001Service.goW001Search(goW001_datePickers_array);
    }

    @PostMapping("/goW001printIreport")
    public String goW001printIreport(@RequestBody Map<String, ArrayList<Map<String, Object>>> params) throws JRException {
        ArrayList<Map<String, Object>> list12 = new ArrayList<>();
        ArrayList<String> pdfPathList = new ArrayList<>();
        params.values().forEach(list12::addAll);
        Collections.reverse(list12);
        System.out.println(list12);
        final String W001Name = "W001";
        String iReportFile = rootDirectory + "server\\ierport\\" + W001Name + ".jrxml";
        String pdfPath = rootDirectory + W001Name + "reportpdf\\";
        if (!CollectionUtils.isEmpty(list12)) {
//            Ireport.folderMkdirsFunction(pdfPath);
            String pdfName = W001Name + RandomUniqueString();
            //新增Ireport的表頭
            Map<String, Object> header = new HashMap<>();

            //輸出PDF
//            Ireport.exportReportFunctionPDF(list12, iReportFile, header, pdfPath + pdfName + ".pdf");
            pdfPathList.add("Success");
            pdfPathList.add(pdfPath);
            logger.info("W001報表資料數量: {}",list12.size());
        }
        return gson.toJson(pdfPathList);
    }

    private String RandomUniqueString(){
        // 產生所有可能的英文字符和數字
        String allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // 將所有字符放入ArrayList中，以便洗牌
        ArrayList<Character> charList = new ArrayList<>();
        for(char c : allCharacters.toCharArray()){
            charList.add(c);
        }
        // 隨機排列字符
        Collections.shuffle(charList);
        // 從ArrayList中選取您需要的字符數量
        int desiredLength = 30; // 設定所需長度
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < desiredLength; i++) {
            randomString.append(charList.get(i));
        }
        return randomString.toString();
    }


}
