package com.project.server.Service.GoW003;

import com.project.server.Mapper.W001Mapper;
import com.project.server.Mapper.W002Mapper;
import com.project.server.Model.GoW0012;
import com.project.server.Model.GoW002;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class W003ServiceImpl implements W003Service {
    @Resource
    private W001Mapper w001Mapper;
    @Resource
    private W002Mapper w002Mapper;

    private ArrayList<GoW0012> getList12(String[] dateRangeArray, String[] fNumeNumberArray) {
        return w001Mapper.goW0012_select_pickers(
                dateRangeArray[0], dateRangeArray[1],
                fNumeNumberArray[0], fNumeNumberArray[1]
        );
    }

    private ArrayList<GoW002> getList2(String[] dateRangeArray, String[] fNumeNumberArray) {
        return w002Mapper.goW002_select_pickers(
                dateRangeArray[0], dateRangeArray[1],
                fNumeNumberArray[0], fNumeNumberArray[1]
        );
    }

    @Override
    public ArrayList<Object> goW003monthProportion(String[] fNumeNumberArray, String[] setDateRangeLastArray, String[] setDateRangeThisArray, String[] setDateRangeNextArray) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(List.of(
                getList12(setDateRangeLastArray, fNumeNumberArray),
                getList12(setDateRangeThisArray, fNumeNumberArray),
                getList12(setDateRangeNextArray, fNumeNumberArray)
        ));
        list.add(List.of(
                getList2(setDateRangeLastArray, fNumeNumberArray),
                getList2(setDateRangeThisArray, fNumeNumberArray),
                getList2(setDateRangeNextArray, fNumeNumberArray)
        ));
        return list;
    }

    @Override
    public ArrayList<Object> goW003seasonProportion(String[] fNumeNumberArray, String[] fourSeasonsArray) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(List.of(
                getList12(fourSeasonsArray, fNumeNumberArray)
        ));
        list.add(List.of(
                getList2(fourSeasonsArray, fNumeNumberArray)
        ));
        return list;
    }


}
