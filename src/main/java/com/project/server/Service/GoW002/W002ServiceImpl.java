package com.project.server.Service.GoW002;

import com.project.server.Entity.GoW002Bean;
import com.project.server.Mapper.W002Mapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class W002ServiceImpl implements W002Service {

    @Resource
    private W002Mapper w002Mapper;

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
        goW002.setNew_date(goW002.getNew_date());
        goW002.setUpate_time(today);
        w002Mapper.goW002_insert(goW002);
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> confirmEventDelete(Map<String, Object> params) {
        GoW002Bean goW002 = new GoW002Bean();
        goW002.setF_name(params.get("f_name").toString());
        goW002.setNumber(params.get("number").toString());
        w002Mapper.goW002_Delete(Integer.parseInt(params.get("id").toString()));
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> goW001Modify(GoW002Bean goW002) {
        Date today = new Date();
        goW002.setNew_date(goW002.getNew_date());
        goW002.setUpate_time(today);
        w002Mapper.goW002_modify(goW002);
        return printTheData(goW002);
    }

}
