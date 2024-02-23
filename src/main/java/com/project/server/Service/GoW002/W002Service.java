package com.project.server.Service.GoW002;

import com.project.server.Entity.GoW002Bean;

import java.util.ArrayList;

public interface W002Service {
    ArrayList<Object> W002UrlDefault(String fName, String number);

    ArrayList<Object> goW002Add(GoW002Bean goW002);
}
