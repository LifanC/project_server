package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;

public interface IndexService {
    boolean[] login(IndexUrlBean indexUrlBean);

    boolean[] register(IndexUrlBean indexUrlBean);

    boolean[] delete(IndexUrlBean indexUrlBean);
}
