package com.project.server.Mapper;

import com.project.server.Entity.GoW0012Bean;
import com.project.server.Entity.GoW001Bean;
import com.project.server.Model.GoW001;
import com.project.server.Model.GoW0012;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface W001Mapper {
    void goW001_insert(GoW001Bean goW001);

    void goW0012_insert(GoW0012Bean goW0012);

    void goW0012_modify(GoW0012Bean goW0012);

    ArrayList<GoW001> goW001_select(GoW001Bean goW001);

    ArrayList<GoW0012> goW0012_select(GoW0012Bean goW0012);

    void goW001_Delete(int id);

    void goW001_modify(GoW001Bean goW001);

    ArrayList<GoW0012> goW0012_select_pickers(String params0, String params1);

    ArrayList<GoW001> goW0012_select_NewDatelist(ArrayList<String> newDatelist);
}
