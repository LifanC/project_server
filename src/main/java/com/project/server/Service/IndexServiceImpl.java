package com.project.server.Service;

import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    @Override
    public boolean insert(IndexUrl indexUrl) {
        if (StringUtils.isBlank(indexUrl.getF_name()) && StringUtils.isBlank(indexUrl.getNumber())) {
            return false;
        } else {
            Date date = new Date();
            IndexUrl list = indexMapper.select(indexUrl);
            if (list == null) {
                indexUrl.setUpate_time(date);
                indexMapper.insert(indexUrl);
            } else {
                indexUrl.setFrequency(list.getFrequency());
                indexUrl.setUpate_time(date);
                indexMapper.update(indexUrl);
            }
            return true;
        }
    }
}
