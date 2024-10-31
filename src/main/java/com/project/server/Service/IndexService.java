package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;

import java.util.List;

public interface IndexService {

    boolean login(IndexUrlBean indexUrlBean);

    List<Object> dialogAdmin();

    List<Object> dialogVisibleMethod(IndexUrlBean indexUrlBean);
}
