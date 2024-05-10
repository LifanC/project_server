package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    @Override
    public boolean[] login(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name()) || StringUtils.isBlank(indexUrlBean.getNumber())) {
            return new boolean[]{false, false};
        }
        ArrayList<IndexUrl> list = indexMapper.select(indexUrlBean);
        return (CollectionUtils.isEmpty(list)) ? new boolean[]{false, false} : new boolean[]{true, true};
    }

    @Override
    public boolean[] register(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name()) || StringUtils.isBlank(indexUrlBean.getNumber())) {
            return new boolean[]{false, false};
        }
        boolean[] result;
        Instant now = Instant.now();
        ArrayList<IndexUrl> list = indexMapper.select(indexUrlBean);
        boolean isEmptyList = CollectionUtils.isEmpty(list);
        result = (isEmptyList) ? new boolean[]{true, true} : new boolean[]{true, false};
        if (isEmptyList) {
            indexUrlBean.setUpate_time(Date.from(now));
            indexMapper.insert(indexUrlBean);
        }
        return result;
    }

    @Override
    public boolean[] delete(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name()) || StringUtils.isBlank(indexUrlBean.getNumber())) {
            return new boolean[]{false, false};
        }
        indexMapper.deleteW001(indexUrlBean);
        indexMapper.deleteW0012(indexUrlBean);
        indexMapper.deleteW002(indexUrlBean);
        boolean deleteList = indexMapper.delete(indexUrlBean) == 1;
        return (deleteList) ? new boolean[]{true, true} : new boolean[]{false, false};
    }

}
