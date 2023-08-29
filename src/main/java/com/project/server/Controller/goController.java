package com.project.server.Controller;

import jakarta.servlet.http.*;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.IntStream;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/go")
public class goController extends HttpServlet {

    @GetMapping("/getGo")
    public String getGo(@PathParam("userName") String userName) {
        String[] arr = {"Luke", "admin"};
        if (Arrays.asList(arr).contains(userName)) {
            return userName;
        } else {
            return "";
        }
    }

}
