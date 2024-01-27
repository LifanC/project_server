package com.project.server.Mapper;

import com.project.server.Model.IndexUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IndexMapper {
    ArrayList<IndexUrl> select(IndexUrl indexUrl);

    void insert(IndexUrl indexUrl);

    void update(IndexUrl indexUrl);

    void delete(IndexUrl indexUrl);
}
