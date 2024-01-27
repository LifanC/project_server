package com.project.server.Mapper;

import com.project.server.Model.GoW001;
import com.project.server.Model.GoW0012;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface W001Mapper {
    void goW001_insert(GoW001 goW001);

    void goW0012_insert(GoW0012 goW0012);

    void goW0012_update(GoW0012 goW0012);

    ArrayList<GoW001> goW001_select(GoW001 goW001);

    ArrayList<GoW0012> goW0012_select(GoW0012 goW0012);

    void confirmEventDelete(int id);

    void modify(GoW001 goW001);
}
