package com.project.server.Controller;

import com.project.server.Entity.AdminIndexUrlBean;
import com.project.server.Entity.IndexUrlBean;
import com.project.server.Service.IndexService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Index")
public class IndexUrlController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexService indexService;

    private static final String ADMIN_ACCOUNT = "admin";
    private static final String ADMIN_PASS = "456123";

    @PostMapping("/indexUrl")
    public boolean indexUrl(@RequestBody IndexUrlBean indexUrlBean) {
        logger.info("Start indexUrl: {}", indexUrlBean);
        boolean judge = indexService.login(indexUrlBean);
        logger.info("indexUrl: 登入{}", (judge) ? "失敗" : "成功");
        return judge;
    }

    @PostMapping("/dialogAdmin")
    public List<Object> dialogAdmin(@RequestBody AdminIndexUrlBean adminIndexUrlBean) {
        logger.info("Start dialogAdmin: {}", adminIndexUrlBean);
        if (!ADMIN_ACCOUNT.equals(adminIndexUrlBean.getAdminAccountNumber()) || !ADMIN_PASS.equals(adminIndexUrlBean.getAdminPassword())) {
            return new ArrayList<>();
        }
        return indexService.dialogAdmin();
    }

    @PostMapping("/dialogVisibleMethod")
    public List<Object> dialogVisibleMethod(@RequestBody IndexUrlBean indexUrlBean) {
        logger.info("Start dialogVisibleMethod: {}", indexUrlBean);
        return indexService.dialogVisibleMethod(indexUrlBean);
    }

}
