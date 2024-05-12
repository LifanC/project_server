package com.project.server.Service.GoW002;

import com.project.server.Entity.GoW002Bean;
import com.project.server.Mapper.W002Mapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class W002ServiceImpl implements W002Service {

    @Resource
    private W002Mapper w002Mapper;

    private String DateFormat(Date newDate) {
        return newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    private ArrayList<Object> printTheData(GoW002Bean goW002) {
        return new ArrayList<>(Collections.singleton(w002Mapper.goW002_select(goW002)));
    }

    @Override
    public ArrayList<Object> W002UrlDefault(String fName, String number) {
        GoW002Bean goW002 = GoW002Bean.builder()
                .f_name(fName)
                .number(number)
                .build();
        return printTheData(goW002);
    }

    @Override
    public boolean goW002Search(GoW002Bean goW002) {
        return w002Mapper.goW002_select_value(goW002).isEmpty();
    }

    @Override
    public ArrayList<Object> goW002Add(GoW002Bean goW002) {
        Instant now = Instant.now();
        goW002.setNew_date(goW002.getNew_date());
        goW002.setUpate_time(Date.from(now));
        w002Mapper.goW002_insert(goW002);
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> confirmEventDelete(Map<String, Object> params) {
        String fName = Objects.toString(params.get("f_name"), "");
        String number = Objects.toString(params.get("number"), "");
        String id = Objects.toString(params.get("id"), "");
        w002Mapper.goW002_Delete(id);
        GoW002Bean goW002 = GoW002Bean.builder()
                .f_name(fName)
                .number(number)
                .build();
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> goW002Modify(GoW002Bean goW002) {
        Instant now = Instant.now();
        goW002.setNew_date(goW002.getNew_date());
        goW002.setUpate_time(Date.from(now));
        w002Mapper.goW002_modify(goW002);
        return printTheData(goW002);
    }

    @Override
    public ArrayList<Object> goW002Query(GoW002Bean goW002) {
        goW002.setNew_date_Format(DateFormat(goW002.getNew_date()));
        return new ArrayList<>(Collections.singleton(w002Mapper.goW002_query(goW002)));
    }


}
