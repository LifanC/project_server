package com.project.server.Mapper;

import com.project.server.Model.GoW002;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface W002Mapper {

    ArrayList<GoW002> goW002_select(GoW002 goW002);

    void goW002_insert(GoW002 goW002);

    void goW002_modify(GoW002 goW002);

    ArrayList<GoW002> goW002_selectAll();
}
