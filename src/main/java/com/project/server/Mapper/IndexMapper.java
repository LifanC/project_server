package com.project.server.Mapper;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Model.IndexUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface IndexMapper {

    List<Map<String, Object>> getPermissions();

    ArrayList<IndexUrl> select(IndexUrlBean indexUrlBean);

    void insert(IndexUrlBean indexUrlBean);

    int delete(IndexUrlBean indexUrlBean);

    void deleteW001(IndexUrlBean indexUrlBean);

    void deleteW0012(IndexUrlBean indexUrlBean);

    void deleteW002(IndexUrlBean indexUrlBean);

    List<Map<String, Object>> selectPrivatedata(Map<String, String> privatedata);

    void insertPrivatedata(Map<String, String> privatedata);

    void insertEncryptionPrivatedata(Map<String, String> privatedata);

    List<Map<String, Object>> selectPrivatedataAll();

    List<Map<String, Object>> selectEncryptionPrivatedata();
}
