package com.project.server.Service.GoW002;

import com.project.server.Entity.GoW002Bean;
import com.project.server.Mapper.W002Mapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class W002ServiceImpl implements W002Service {

    @Resource
    private W002Mapper w002Mapper;

    private String DateFormat(Date newDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(newDate);
    }

    private ArrayList<Object> printTheData(GoW002Bean goW002) {
        ArrayList<Object> alo = new ArrayList<>();
        alo.add(w002Mapper.goW002_select(goW002));
        return alo;
    }

    @Override
    public ArrayList<Object> W002UrlDefault(String fName, String number) {
        GoW002Bean goW002 = new GoW002Bean();
        goW002.setF_name(fName);
        goW002.setNumber(number);
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> goW002Add(GoW002Bean goW002) {
        Date today = new Date();
        goW002.setUpate_day(DateFormat(today));
        goW002.setUpate_time(today);
        w002Mapper.goW002_insert(goW002);
        return printTheData(goW002);
    }

}
