package com.project.server.Controller;

import com.google.gson.Gson;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/go")
public class goController {
    private final List<String> arr = new ArrayList<>();
    private final Gson gson = new Gson();

    /**
     * <h3>home登入功能</h3>
     * @param params 前端fromData的值
     * @return 回傳UserName值
     */
    @PostMapping("/getGo")
    public String getGo(@RequestBody Map<String, String> params) {
        if (arr.contains(params.get("data"))) {
            return gson.toJson(params.get("data"));
        } else {
            return gson.toJson("");
        }
    }

    /**
     * <h3>home查詢UserName功能</h3>
     * @return 回傳UserName...
     */
    @GetMapping("/getUserName")
    public String getUserName() {
        return gson.toJson(arr);
    }

    Random random = new Random();
    /**
     * <h3>home三十秒換UserName的功能</h3>
     */
    @Scheduled(fixedRate = 30000)
    public void runTask() {
        // 在這裡放置您的任務邏輯
        arr.clear();
        //UserName -> 預設5種
        int z = 5;
        for (int i = 0; i < z; i++) {
            arr.add(asciiUserName());
        }
    }

    /**
     * <h3>隨機換UserName的功能</h3>
     * @return 回傳 例:Sl9560
     */
    private String asciiUserName() {
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
     * @return 回傳true
     * @throws InterruptedException Thread.sleep
     */
    @GetMapping("/time")
    public String time() throws InterruptedException {
        Thread.sleep(600000);
        return gson.toJson(true);
    }

}
