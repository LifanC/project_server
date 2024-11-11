package com.project.server.Mapper;

import com.project.server.Entity.W002Bean;
import com.project.server.Model.W001;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface W002Mapper {
    List<Map<String, Object>> select(Map<String, Object> param);

    List<W001> selectW001x(W002Bean w002Bean);

    List<W001> selectW001h(W002Bean w002Bean);
}
