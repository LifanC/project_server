package com.project.server.Service;

import com.project.server.Model.IndexUrl;

public interface IndexService {
    boolean login(IndexUrl indexUrl);

    boolean register(IndexUrl indexUrl);

    boolean delete(IndexUrl indexUrl);
}
