package com.project.server.mapper;

import com.project.server.Model.A;
import com.project.server.Model.B;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    void insA(Map<String,String> map);
    void insB(Map<String,String> map);
    List<A> selectA(String date);
    List<B> selectB(String date);
    void updateData(String date,Integer AexM,Integer BinM);
    List<B> find(Map<String,String> map);
    List<A> findA(List<String> listDate);
    void del(Map<String, String> a_id);
    void setUpdate(Map<String, String> map);
}
