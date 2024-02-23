package com.project.server.Mapper;

import com.project.server.Entity.GoW002Bean;
import com.project.server.Model.GoW002;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface W002Mapper {
    void goW002_insert(GoW002Bean goW002);


    ArrayList<GoW002> goW002_select(GoW002Bean goW002);
}
