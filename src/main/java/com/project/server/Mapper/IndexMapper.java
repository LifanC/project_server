package com.project.server.Mapper;

import com.project.server.Model.IndexUrl;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndexMapper {
    IndexUrl select(IndexUrl indexUrl);

    void insert(IndexUrl indexUrl);

    void update(IndexUrl indexUrl);
}
