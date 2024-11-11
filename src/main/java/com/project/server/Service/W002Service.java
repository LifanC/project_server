package com.project.server.Service;

import com.project.server.Entity.W002Bean;

import java.util.List;

public interface W002Service {
    List<Object> selectData(W002Bean w002Bean);

    List<Object> selectDatah(W002Bean w002Bean);

    List<Object> selectDatax(W002Bean w002Bean);
}
