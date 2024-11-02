package com.project.server.Mapper;

import com.project.server.Entity.W001Bean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface W001Mapper {
    void create(Map<String, Object> param);
    void createh(Map<String, Object> param);
    void createBatchh(List<Map<String, Object>> dataParams);
    String maxNumber(W001Bean w001Bean);
}
