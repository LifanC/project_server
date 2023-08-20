package com.project.server.mapper;

import com.project.server.Model.TableName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetUpMapper {

    List<TableName> getTable();

}
