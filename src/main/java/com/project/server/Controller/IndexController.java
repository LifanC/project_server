package com.project.server.Controller;

import com.google.gson.Gson;
import com.project.server.Common.Ireport;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/index")
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson = new Gson();
    private final String rootDirectory = "D:\\project3.0\\";
    @Resource
    private IndexService indexService;

    /**
     * <h3>index新增功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @PostMapping("/ins")
    public String ins(@RequestBody Map<String, Map<String, String>> params) {
        logger.info("index新增功能");
        Map<String, String> mapA = new HashMap<>();
        params.forEach((key, value) -> mapA.putAll(value));
        return gson.toJson(indexService.ins(mapA));
    }

    /**
     * <h3>index查詢功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @PostMapping("fin")
    public String fin(@RequestBody Map<String, String> params) {
        logger.info("index查詢功能: {}","fin");
        logger.info("單一日期查詢: {}","Table B");
        return gson.toJson(indexService.fin(params));
    }

    /**
     * <h3>index查詢功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @PostMapping("finA")
    public String finA(@RequestBody Map<String, String> params) {
        logger.info("index查詢功能: {}","finA");
        logger.info("單一日期查詢: {}","Table A");
        return gson.toJson(indexService.finA(params));
    }

    /**
     * <h3>index查詢功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @PostMapping("find")
    public String find(@RequestBody Map<String, String[]> params) {
        logger.info("index查詢功能: {}","find");
        logger.info("Start~End日期查詢: {}","Table B");
        return gson.toJson(indexService.find(params));
    }

    /**
     * <h3>index查詢功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @PostMapping("findA")
    public String findA(@RequestBody Map<String, List<Map<String, Object>>> params) {
        logger.info("index查詢功能: {}","findA");
        logger.info("查詢資料明細: {}","Table A");
        return gson.toJson(indexService.findA(params));
    }

    /**
     * <h3>index刪除功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @PostMapping("/del")
    public String del(@RequestBody Map<String, Map<String, String>> params) {
        logger.info("刪除資料明細");
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return gson.toJson(indexService.del(valuesMap));
    }

    /**
     * <h3>index修改資料明細功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @PostMapping("/enter")
    public String enter(@RequestBody Map<String, Map<String, String>> params) {
        logger.info("修改資料明細");
        Map<String, String> valuesMap = new HashMap<>();
        params.forEach((key, value) -> valuesMap.putAll(value));
        return gson.toJson(indexService.enter(valuesMap));
    }

    private static String ReTitleName = "";
    @GetMapping("/printIreport")
    public void printIreport(@PathParam("titleName") String titleName) {
        logger.info("index列印報表名稱 {}",titleName);
        ReTitleName = titleName;
    }
    /**
     * <h3>index列印報表</h3>
     *
     * @param params 前端fromData的值
     * @return 輸出PDF路徑位置
     * @throws JRException Jasper JRException
     */
    @PostMapping("/printIreport")
    public String printIreport(@RequestBody Map<String, List<Map<String, Object>>> params) throws JRException {
        List<Map<String, Object>> listB = new ArrayList<>();
        params.values().forEach(listB::addAll);
        //listB值前後對調
        Collections.reverse(listB);
        final String reportBName = "reportB";
        if (!listB.isEmpty()) {
            List<String> stringList = new ArrayList<>();
            listB.forEach(list -> stringList.add(list.get("date").toString()));
            String str = stringList.get(0) + stringList.get(stringList.size() - 1);
            String dateStr = str.replace("-", "");
            String iReportFile = rootDirectory + "server\\ierport\\" + reportBName + ".jrxml";
            String pdfPath = rootDirectory + "reportBpdf\\";
            Ireport.folderMkdirsFunction(pdfPath);
            String time = dateStr + RandomUniqueString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String toDay = sdf.format( new Date());
            //新增Ireport的表頭
            Map<String, Object> map = new HashMap<>();
            map.put("title", ReTitleName + "'s   Monthly Report");
            map.put("reportBName",reportBName);
            map.put("dateStr1", stringList.get(0));
            map.put("dateStr2", stringList.get(stringList.size() - 1));
            map.put("time", time);
            map.put("toDay", toDay);
            //輸出PDF
            Ireport.exportReportFunctionPDF(listB, iReportFile, map, pdfPath + time + ".pdf");
            List<Object> pdfPathList = new ArrayList<>();
            pdfPathList.add(pdfPath);
            logger.info("index報表資料數量: {}",listB.size());
            return gson.toJson(pdfPathList);
        }
        return gson.toJson("err");
    }

    /**
     * <h3>index畫面顯示報表名稱</h3>
     *
     * @param fileName fileName名稱
     * @return 顯示當月fileName名稱
     */
    @GetMapping("/printIreportData")
    public String printIreportData(@PathParam("fileName") String fileName) {
        File folder = new File(rootDirectory + fileName + "\\");
        List<String> list = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            for (String s : Objects.requireNonNull(folder.list())) {
                list.add(s.substring(16));
            }
            return gson.toJson(list);
        } else {
            return gson.toJson("");
        }
    }

    /**
     * <h3>index查詢報表路徑位置</h3>
     *
     * @param fileName fileName名稱
     * @return 顯示PDF路徑位置
     */
    @GetMapping("/printPath")
    public String printPath(@PathParam("fileName") String fileName) {
        String pdfPath = rootDirectory + fileName + "\\";
        return gson.toJson(pdfPath);
    }

    public String RandomUniqueString(){
        // 產生所有可能的英文字符和數字
        String allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // 將所有字符放入List中，以便洗牌
        List<Character> charList = new ArrayList<>();
        for(char c : allCharacters.toCharArray()){
            charList.add(c);
        }
        // 隨機排列字符
        Collections.shuffle(charList);
        // 從List中選取您需要的字符數量
        int desiredLength = 30; // 設定所需長度
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < desiredLength; i++) {
            randomString.append(charList.get(i));
        }
        return randomString.toString();
    }

}









