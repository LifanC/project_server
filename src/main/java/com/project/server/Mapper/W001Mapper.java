package com.project.server.Mapper;

import com.project.server.Entity.W001Bean;
import com.project.server.Model.W001;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface W001Mapper {
    int create(Map<String, Object> param);
    void createh(Map<String, Object> param);
    void createBatchh(List<Map<String, Object>> dataParams);
    String maxNumber(W001Bean w001Bean);
    List<W001> select(W001Bean w001Bean);
    void update(Map<String, Object> param);
    void delete(Map<String, Object> param);
}
