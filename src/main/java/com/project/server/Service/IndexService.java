package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Model.IndexUrl;

import java.util.List;
import java.util.Map;

public interface IndexService {

    List<Map<String, Object>> indexUrlDefault();

    boolean[] login(IndexUrlBean indexUrlBean);

    boolean[] register(IndexUrlBean indexUrlBean);

    boolean[] delete(IndexUrlBean indexUrlBean);

    List<IndexUrl> indexUrlPermissions(IndexUrlBean indexUrlBean);
}
