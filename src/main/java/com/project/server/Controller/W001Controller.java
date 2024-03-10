package com.project.server.Controller;

//import com.google.gson.Gson;
import com.project.server.Common.Ireport;
import com.project.server.Entity.GoW0012Bean;
import com.project.server.Entity.GoW001Bean;
import com.project.server.Service.GoW001.W001Service;
import jakarta.annotation.Resource;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W001")
public class W001Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String rootDirectory = "D:\\project3.0\\";
//    private final Gson gson = new Gson();
    @Resource
    private W001Service w001Service;

    @GetMapping("/goW001")
    public String goW001() {
        logger.info("Start goW001");
        return "記帳系統";
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
    public ArrayList<Object> goW001Add(@RequestBody Map<String, GoW001Bean> params) {
        GoW001Bean goW001 = params.get("GoW001");
        logger.info("Start goW001Add: {}", goW001);
        try {
            new BigDecimal(String.valueOf(goW001.getInput_money()));
            return w001Service.goW001Add(goW001);
        } catch (NumberFormatException e) {
            logger.info("Start goW001Add Input_money: {}", goW001.getInput_money());
            logger.info("NumberFormatException: {}", e.getMessage());
            return null;
        }
    }

    @PostMapping("/goW001Single_search")
    public ArrayList<Object> goW001Single_search(@RequestBody Map<String, GoW001Bean> params) {
        GoW001Bean goW001 = params.get("GoW001");
        logger.info("Start goW001Single_search: {}", goW001);
        return w001Service.goW001Single_search(goW001);
    }

    @DeleteMapping("/confirmEventDelete")
    public ArrayList<Object> confirmEventDelete(@RequestParam Map<String, Object> params) {
        logger.info("Start W001 confirmEventDelete: {}", params);
        return w001Service.confirmEventDelete(params);
    }

    @PutMapping("/goW001Modify")
    public ArrayList<Object> goW001Modify(@RequestBody Map<String, GoW001Bean> params) {
        GoW001Bean goW001 = params.get("GoW001");
        logger.info("Start goW001Modify: {}", goW001);
        try {
            new BigDecimal(String.valueOf(goW001.getInput_money()));
            return w001Service.goW001Modify(goW001);
        } catch (NumberFormatException e) {
            logger.info("Start goW001Modify Input_money: {}", goW001.getInput_money());
            logger.info("NumberFormatException: {}", e.getMessage());
            return null;
        }
    }

    @PostMapping("/goW001Search")
    public ArrayList<Object> goW001Search(@RequestBody Map<String, String[]> params) {
        String[] goW001_datePickers_array = params.getOrDefault("GoW001_datePicker", new String[0]);
        String[] GoW001_fNume_number_array = params.getOrDefault("GoW001_fNume_number", new String[0]);
        String[] combinedArray = Stream.concat(
                Arrays.stream(goW001_datePickers_array), Arrays.stream(GoW001_fNume_number_array)
        ).toArray(String[]::new);
        String goW001SearchLog = String.join(",", combinedArray);
        logger.info("Start goW001Search: {}", goW001SearchLog);
        return w001Service.goW001Search(combinedArray);
    }

    @PostMapping("/goW001printIreport")
    public ArrayList<String> goW001printIreport(
            @RequestBody Map<String, ArrayList<Map<String, Object>>> params) throws JRException, IOException {
        ArrayList<Map<String, Object>> listData = params.values().stream().flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));

        // 將日期格式化並排序
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        listData.sort(Comparator.comparing(
                o -> LocalDate.parse(o.get("new_date_Format").toString().replace("-", ""), formatter)));

        ArrayList<String> pdfPathList = new ArrayList<>();
        Collections.reverse(listData);

        final String W001Name = "W001";
        String pdfPath = rootDirectory + W001Name + "reportpdf/";
        String iReportFilePdfPath = rootDirectory + "server/ierport/";
        String iReportFile = iReportFilePdfPath + W001Name + ".jrxml";

        if (CollectionUtils.isEmpty(listData)) {
            pdfPathList.add("Fail");
            pdfPathList.add("");
            logger.info("W001報表資料數量: {}", 0);

        } else {
            Files.createDirectories(Paths.get(pdfPath));
            Files.createDirectories(Paths.get(iReportFilePdfPath));
            String pdfName = W001Name + RandomUniqueString();
            String folderNames_pdf = pdfPath + pdfName;

            //新增Ireport的表頭
            Map<String, Object> header = new HashMap<>(Map.ofEntries(
                    Map.entry("title", "W001 Report"),
                    Map.entry("W001Name", W001Name),
                    Map.entry("pdfName", pdfName),
                    Map.entry("toDay", LocalDate.now().format(formatter))
            ));

            // 輸出PDF
            Ireport.exportReportFunctionPDF(listData, iReportFile, header, folderNames_pdf + ".pdf");
            pdfPathList.add("Success");
            pdfPathList.add(folderNames_pdf + ".pdf");
            logger.info("W001報表資料數量: {}", listData.size());
        }
        return pdfPathList;
    }

    private String RandomUniqueString() {
        // 產生所有可能的英文字符和數字
        List<Character> characters = IntStream.range('A', 'Z' + 1)
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        // 將所有字符隨機排列
        Collections.shuffle(characters);
        // 從字符列表中選取您需要的字符數量
        int desiredLength = 5; // 設定所需長度
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < desiredLength; i++) {
            int randomIndex = random.nextInt(characters.size());
            randomString.append(characters.get(randomIndex));
        }
        return randomString.toString();
    }


    @PostMapping("/goW001proportion")
    public ArrayList<Object> W001proportion(@RequestBody GoW0012Bean goW0012Bean) {
        logger.info("Start W001proportion: {}", goW0012Bean);
        return w001Service.W001proportion(goW0012Bean);
    }

    @PostMapping("/goW001proportionSingle_search")
    public ArrayList<Object> goW001proportionSingle_search(@RequestBody GoW001Bean goW001Bean) {
        logger.info("Start goW001proportionSingle_search: {}", goW001Bean);
        return w001Service.goW001proportionSingle_search(goW001Bean);
    }

    @PostMapping("/goW001monthProportion")
    public ArrayList<Object> goW001monthProportion(@RequestBody Map<String, String[]> params) {
        String[] GoW001_fNume_number_array = params.getOrDefault("GoW001_fNume_number", new String[0]);
        String[] GoW001_setDateRange_array = params.getOrDefault("GoW001_setDateRange", new String[0]);
        String[] combinedArray = Stream.concat(
                Arrays.stream(GoW001_setDateRange_array), Arrays.stream(GoW001_fNume_number_array)
        ).toArray(String[]::new);
        String goW001monthProportionLog = String.join(",", combinedArray);
        logger.info("Start goW001monthProportion: {}", goW001monthProportionLog);
        return w001Service.goW001monthProportion(combinedArray);
    }
}
