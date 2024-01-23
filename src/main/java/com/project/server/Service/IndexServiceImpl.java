package com.project.server.Service;

import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    @Override
    public boolean login(IndexUrl indexUrl) {
        ArrayList<IndexUrl> list = indexMapper.select(indexUrl);
        return !CollectionUtils.isEmpty(list);
    }

    @Override
    public boolean register(IndexUrl indexUrl) {
        if (StringUtils.isBlank(indexUrl.getF_name()) || StringUtils.isBlank(indexUrl.getNumber())) {
            return false;
        } else {
            Date date = new Date();
            ArrayList<IndexUrl> list = indexMapper.select(indexUrl);
            if (CollectionUtils.isEmpty(list)) {
                indexUrl.setUpate_time(date);
                indexMapper.insert(indexUrl);
            } else {
                list = indexMapper.select(indexUrl);
                list.forEach(e -> indexUrl.setFrequency(e.getFrequency()));
                indexUrl.setUpate_time(date);
                indexMapper.update(indexUrl);
            }
            return true;
        }
    }

    @Override
    public boolean delete(IndexUrl indexUrl) {
        if (StringUtils.isBlank(indexUrl.getF_name()) || StringUtils.isBlank(indexUrl.getNumber())) {
            return false;
        } else {
            indexMapper.delete(indexUrl);
            return true;
        }
    }
}
