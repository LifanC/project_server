package com.project.server.Service.GoW003;

import java.util.ArrayList;

public interface W003Service {

    ArrayList<Object> goW003monthProportion(
            String[] fNumeNumberArray,
            String[] setDateRangeThisArray,
            String[] setDateRangeLastArray
    );
}
