package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Entity.W002Bean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Mapper.W002Mapper;
import com.project.server.Model.IndexUrl;
import com.project.server.Model.W001;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class W002ServiceImpl implements W002Service {

    @Resource
    private IndexMapper indexMapper;

    @Resource
    private W002Mapper w002Mapper;

    private String DateFormat(Date newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
    }

    @Override
    public List<Object> selectData(W002Bean w002Bean) {
        IndexUrlBean indexUrlBean = IndexUrlBean.builder().accountNumber(w002Bean.getAccountNumber()).password(w002Bean.getPassword()).build();
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        String accountNumber = "";
        String dataNumber = "";
        for (IndexUrl listData : list) {
            accountNumber = listData.getAccountNumber();
            dataNumber = listData.getDataNumber();
        }

        Map<String, Object> param = new HashMap<>();
        String date = DateFormat(new Date());
        param.put("update_time", date);
        param.put("accountNumber", accountNumber);
        param.put("dataNumber", dataNumber);
        param.put("type", w002Bean.getType());
        List<Map<String, Object>> select = w002Mapper.select(param);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }

    @Override
    public List<Object> selectDatax(W002Bean w002Bean) {
        List<W001> select = w002Mapper.selectW001x(w002Bean);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }

    @Override
    public List<Object> selectDatah(W002Bean w002Bean) {
        List<W001> select = w002Mapper.selectW001h(w002Bean);
        List<Object> result = new ArrayList<>();
        result.add(select);
        return result;
    }
}
