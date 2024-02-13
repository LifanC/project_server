package com.project.server.Service.GoW002;

import com.project.server.Mapper.W002Mapper;
import com.project.server.Model.GoW002;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class W002ServiceImpl implements W002Service {

    @Resource
    private W002Mapper w002Mapper;

    @Override
    public String singleWord(GoW002 goW002) {
        String returnStr = "";
        switch (goW002.getSingleWordVal()) {
            case "7000Word" -> {
                goW002.setINPUT_EN(goW002.getInput7000En());
                goW002.setINPUT_CN(goW002.getInput7000Cn());
                returnStr = "7000Word 成功";
            }
            case "TOEICWord" -> {
                goW002.setINPUT_EN(goW002.getInputTOEICEn());
                goW002.setINPUT_CN(goW002.getInputTOEICCn());
                returnStr = "TOEICWord 成功";
            }
        }
        ArrayList<GoW002> list = w002Mapper.goW002_select(goW002);
        if (list.isEmpty()) {
            w002Mapper.goW002_insert(goW002);
        } else {
            w002Mapper.goW002_modify(goW002);
        }
        return returnStr;
    }

    @Override
    public ArrayList<GoW002> goW002Show() {
        return w002Mapper.goW002_selectAll();
    }
}
