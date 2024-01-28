package com.project.server.Service.GoW001;

import com.project.server.Model.GoW001;

import java.util.ArrayList;
import java.util.Map;

public interface W001Service {
    ArrayList<Object> goW001Add(GoW001 goW001);

    ArrayList<Object> goW001Single_search(GoW001 goW001);

    ArrayList<Object> W001UrlDefault(String fName, String number);

    ArrayList<Object> confirmEventDelete(Map<String, Object> params);

    ArrayList<Object> goW001Modify(GoW001 goW001);

    ArrayList<Object> goW001Search(String[] goW001DatePickersArray);
}
