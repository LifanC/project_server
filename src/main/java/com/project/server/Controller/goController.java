package com.project.server.Controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/go")
public class goController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<String> arr = new ArrayList<>();
    private final Gson gson = new Gson();

    /**
     * <h3>home登入功能</h3>
     *
     * @param params 前端fromData的值
     * @return 回傳UserName值
     */
    @PostMapping("/getGo")
    public String getGo(@RequestBody Map<String, String> params) {
        if (arr.contains(params.get("data"))) {
            String arrName = "";
            switch (params.get("data").substring(0, 1)) {
                case "A" -> arrName = "Alice Adams";
                case "B" -> arrName = "Benjamin Bennett";
                case "C" -> arrName = "Catherine Clark";
                case "D" -> arrName = "David Davis";
                case "E" -> arrName = "Emily Evans";
                case "F" -> arrName = "Franklin Foster";
                case "G" -> arrName = "Grace Gray";
                case "H" -> arrName = "Henry Harris";
                case "I" -> arrName = "Isabella Ingram";
                case "J" -> arrName = "James Johnson";
                case "K" -> arrName = "Karen King";
                case "L" -> arrName = "Laura Lewis";
                case "M" -> arrName = "Michael Mitchell";
                case "N" -> arrName = "Natalie Nelson";
                case "O" -> arrName = "Olivia Olson";
                case "P" -> arrName = "Peter Parker";
                case "Q" -> arrName = "Quentin Quinn";
                case "R" -> arrName = "Rachel Robinson";
                case "S" -> arrName = "Samuel Smith";
                case "T" -> arrName = "Taylor Turner";
                case "U" -> arrName = "Ulysses Underwood";
                case "V" -> arrName = "Victoria Vaughn";
                case "W" -> arrName = "William Walker";
                case "X" -> arrName = "Xavier Xavier";
                case "Y" -> arrName = "Yvonne Young";
                case "Z" -> arrName = "Zachary Zimmerman";
            }
            logger.info("登入功能: {} 使用者名稱: {}","登入成功",arrName);
            return gson.toJson(arrName);
        } else {
            logger.info("登入功能: {}","登入失敗");
            return gson.toJson("");
        }
    }

    /**
     * <h3>home查詢UserNameAccount功能</h3>
     *
     * @return 回傳UserNameAccount...
     */
    @GetMapping("/getUserNameAccount")
    public String getUserName() {
        logger.info("查詢使用者帳號: {}",arr);
        return gson.toJson(arr);
    }

    /**
     * <h3>home三十秒換UserNameAccount功能</h3>
     */
    @Scheduled(fixedRate = 30000)
    public void runTask() {
        arr.clear();
        //UserNameKinds -> 預設5種
        int userNameKinds = 5;
        for (int i = 0; i < userNameKinds; i++) {
            arr.add(asciiUserName());
        }
    }

    /**
     * <h3>隨機換UserNameAccount功能</h3>
     *
     * @return 回傳 例:Sl9560
     */
    private String asciiUserName() {
        Random random = new Random();
        //ASCII碼(65'A'~90'Z'、97'a'~122'z')
        int min1 = 65;
        int max1 = 90;
        int randomInRange1 = random.nextInt(max1 - min1 + 1) + min1;
        String asciiString1 = Character.toString(randomInRange1);
        int min2 = 97;
        int max2 = 112;
        int randomInRange2 = random.nextInt(max2 - min2 + 1) + min2;
        String asciiString2 = Character.toString(randomInRange2);
        int min3 = 0;
        int max3 = 9999;
        int randomInRange3 = random.nextInt(max3 - min3 + 1) + min3;
        String randomInRangeStr3 = String.format("%04d", randomInRange3);
        return asciiString1 + asciiString2 + randomInRangeStr3;
    }

    /**
     * <h3>home自動登出功能</h3>
     *
     * @return 回傳true
     * @throws InterruptedException Thread.sleep
     */
    @GetMapping("/time")
    public String time() throws InterruptedException {
        Thread.sleep(600000);
        logger.info("自動登出");
        return gson.toJson(true);
    }

}
