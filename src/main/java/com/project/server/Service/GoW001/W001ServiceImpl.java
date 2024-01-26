package com.project.server.Service.GoW001;

import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.GoW001;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

@Service
@Transactional
public class W001ServiceImpl implements W001Service {
    @Resource
    private W001Mapper w001Mapper;

    private String DateFormat(Date newDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(newDate);
    }

    @Override
    public ArrayList<GoW001> goW001Add(GoW001 goW001) {
        Date date = new Date();
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        goW001.setUpate_time(date);
        w001Mapper.goW001_insert(goW001);
        return w001Mapper.goW001_select(goW001);
    }

    @Override
    public ArrayList<GoW001> goW001Single_search(GoW001 goW001) {
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        return w001Mapper.goW001_select(goW001);
    }

    @Override
    public ArrayList<GoW001> W001UrlDefault(String fName, String number) {
        GoW001 goW001 = new GoW001();
        goW001.setF_name(fName);
        goW001.setNumber(number);
        return w001Mapper.goW001_select(goW001);
    }

    @Override
    public ArrayList<GoW001> confirmEventDelete(int id, String new_date_Format, String f_name, String number) {
        w001Mapper.confirmEventDelete(id);
        GoW001 goW001 = new GoW001();
        goW001.setNew_date_Format(new_date_Format);
        goW001.setF_name(f_name);
        goW001.setNumber(number);
        return w001Mapper.goW001_select(goW001);
    }

    @Override
    public ArrayList<GoW001> modify(GoW001 goW001) {
        Date date = new Date();
        goW001.setUpate_time(date);
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        w001Mapper.modify(goW001);
        return w001Mapper.goW001_select(goW001);
    }
}
