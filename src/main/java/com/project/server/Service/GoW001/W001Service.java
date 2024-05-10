package com.project.server.Service.GoW001;

import com.project.server.Entity.GoW0012Bean;
import com.project.server.Entity.GoW001Bean;

import java.util.ArrayList;
import java.util.Map;

public interface W001Service {
    ArrayList<Object> goW001Add(GoW001Bean goW001);

    ArrayList<Object> goW001Single_search(GoW001Bean goW001);

    ArrayList<Object> W001UrlDefault(String fName, String number, String permissions_value);

    ArrayList<Object> confirmEventDelete(Map<String, Object> params);

    ArrayList<Object> goW001Modify(GoW001Bean goW001);

    ArrayList<Object> goW001Search(String[] combinedArray);

    ArrayList<Object> W001proportion(GoW0012Bean goW0012);

    ArrayList<Object> goW001proportionSingle_search(GoW001Bean goW001Bean);

    ArrayList<Object> goW001monthProportion(String[] combinedArray);
}
