package com.project.server.Controller;

//import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/W005")
public class W005Controller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final Gson gson = new Gson();

    @GetMapping("/goW005")
    public String goW001() {
        logger.info("Start goW005");
        return "JokeAPI";
    }

}
