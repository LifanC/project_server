package com.project.server.Mapper;

import com.project.server.Model.IndexUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IndexMapper {
    ArrayList<IndexUrl> select(IndexUrl indexUrl);

    void insert(IndexUrl indexUrl);

    void update(IndexUrl indexUrl);

    int delete(IndexUrl indexUrl);

    void deleteW001(IndexUrl indexUrl);

    void deleteW0012(IndexUrl indexUrl);

    void deleteW002(IndexUrl indexUrl);
}
