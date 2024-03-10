package com.project.server.Service.GoW003;

import com.project.server.Entity.GoW0012Bean;
import com.project.server.Entity.GoW001Bean;
import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.GoW0012;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class W003ServiceImpl implements W003Service {
    @Resource
    private W001Mapper w001Mapper;

    private ArrayList<Object> printTheData(ArrayList<GoW0012> list12This, ArrayList<GoW0012> list12Last) {
        return new ArrayList<>(List.of(list12This, list12Last));
    }

    private ArrayList<GoW0012> getList12(String[] dateRangeArray, String[] fNumeNumberArray) {
        return w001Mapper.goW0012_select_pickers(
                dateRangeArray[0], dateRangeArray[1],
                fNumeNumberArray[0], fNumeNumberArray[1]
        );
    }

    @Override
    public ArrayList<Object> goW003monthProportion(
            String[] fNumeNumberArray,
            String[] setDateRangeThisArray,
            String[] setDateRangeLastArray
    ) {
        ArrayList<GoW0012> list12This = getList12(setDateRangeThisArray, fNumeNumberArray);
        ArrayList<GoW0012> list12Last = getList12(setDateRangeLastArray, fNumeNumberArray);
        return printTheData(list12This, list12Last);
    }
}
