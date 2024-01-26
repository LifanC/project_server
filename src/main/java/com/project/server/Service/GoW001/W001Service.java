package com.project.server.Service.GoW001;

import com.project.server.Model.GoW001;

import java.util.ArrayList;

public interface W001Service {
    ArrayList<GoW001> goW001Add(GoW001 goW001);

    ArrayList<GoW001> goW001Single_search(GoW001 goW001);

    ArrayList<GoW001> W001UrlDefault(String fName, String number);

    ArrayList<GoW001> confirmEventDelete(int id, String new_date_Format, String f_name, String number);

    ArrayList<GoW001> modify(GoW001 goW001);
}
