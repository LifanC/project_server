package com.project.server.Mapper;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Model.IndexUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexMapper {
    List<IndexUrl> select(IndexUrlBean indexUrlBean);

    List<IndexUrl> selecth(IndexUrlBean indexUrlBean);

    void create(IndexUrlBean indexUrlBean);

    void createh(IndexUrlBean indexUrlBean);

    int update(IndexUrlBean indexUrlBean);

    int delete(IndexUrlBean indexUrlBean);

    String maxSelect(IndexUrlBean indexUrlBean);
}
