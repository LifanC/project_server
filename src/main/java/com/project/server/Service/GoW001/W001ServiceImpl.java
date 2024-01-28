package com.project.server.Service.GoW001;

import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.GoW001;
import com.project.server.Model.GoW0012;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;

@Service
@Transactional
public class W001ServiceImpl implements W001Service {
    @Resource
    private W001Mapper w001Mapper;

    private String DateFormat(Date newDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(newDate);
    }

    private ArrayList<Object> printTheData(GoW001 goW001, GoW0012 goW0012) {
        ArrayList<Object> alo = new ArrayList<>();
        alo.add(w001Mapper.goW001_select(goW001));
        alo.add(w001Mapper.goW0012_select(goW0012));
        return alo;
    }

    private void shared_method(ArrayList<GoW0012> list12, GoW001 goW001, GoW0012 goW0012) {
        BigDecimal AexM = BigDecimal.ZERO;
        BigDecimal BinM = BigDecimal.ZERO;
        for (GoW0012 l12 : list12) {
            switch (goW001.getExpense_and_income_number()) {
                case "A" -> {
                    AexM = AexM.add(l12.getExpense().add(goW001.getInput_money()));
                    BinM = BinM.add(l12.getIncome());
                }
                case "B" -> {
                    AexM = AexM.add(l12.getExpense());
                    BinM = BinM.add(l12.getIncome().add(goW001.getInput_money()));
                }
            }
        }
        goW0012.setExpense(AexM);
        goW0012.setIncome(BinM);
        goW0012.setTotle_money(BinM.subtract(AexM));
    }

    @Override
    public ArrayList<Object> goW001Add(GoW001 goW001) {
        GoW0012 goW0012 = new GoW0012();
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        goW001.setUpate_time(goW001.getNew_date());
        BeanUtils.copyProperties(goW001, goW0012);
        w001Mapper.goW001_insert(goW001);
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select(goW0012);
        BigDecimal AexM = BigDecimal.ZERO;
        BigDecimal BinM = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(list12)) {
            switch (goW001.getExpense_and_income_number()) {
                case "A" -> AexM = AexM.add(goW001.getInput_money());
                case "B" -> BinM = BinM.add(goW001.getInput_money());
            }
            goW0012.setExpense(AexM);
            goW0012.setIncome(BinM);
            goW0012.setTotle_money(BinM.subtract(AexM));
            w001Mapper.goW0012_insert(goW0012);
        } else {
            shared_method(list12, goW001, goW0012);
            w001Mapper.goW0012_modify(goW0012);
        }
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> goW001Single_search(GoW001 goW001) {
        GoW0012 goW0012 = new GoW0012();
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        BeanUtils.copyProperties(goW001, goW0012);
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> W001UrlDefault(String fName, String number) {
        GoW001 goW001 = new GoW001();
        GoW0012 goW0012 = new GoW0012();
        goW001.setF_name(fName);
        goW001.setNumber(number);
        BeanUtils.copyProperties(goW001, goW0012);
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> confirmEventDelete(Map<String, Object> params) {
        GoW001 goW001 = new GoW001();
        GoW0012 goW0012 = new GoW0012();
        goW0012.setNew_date_Format(params.get("new_date_Format").toString());
        goW0012.setF_name(params.get("f_name").toString());
        goW0012.setNumber(params.get("number").toString());
        goW0012.setUpate_time(new Date());
        BeanUtils.copyProperties(goW0012, goW001);
        goW001.setExpense_and_income_number(params.get("expense_and_income_number").toString());
        goW001.setInput_money(new BigDecimal(params.get("input_money").toString()));
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select(goW0012);
        w001Mapper.goW001_Delete(Integer.parseInt(params.get("id").toString()));
        BigDecimal AexM = BigDecimal.ZERO;
        BigDecimal BinM = BigDecimal.ZERO;
        for (GoW0012 l12 : list12) {
            switch (goW001.getExpense_and_income_number()) {
                case "A" -> {
                    AexM = AexM.add(l12.getExpense().subtract(goW001.getInput_money()));
                    BinM = BinM.add(l12.getIncome());
                }
                case "B" -> {
                    AexM = AexM.add(l12.getExpense());
                    BinM = BinM.add(l12.getIncome().subtract(goW001.getInput_money()));
                }
            }
        }
        goW0012.setExpense(AexM);
        goW0012.setIncome(BinM);
        goW0012.setTotle_money(BinM.subtract(AexM));
        w001Mapper.goW0012_modify(goW0012);
        BeanUtils.copyProperties(goW001, goW0012);
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> goW001Modify(GoW001 goW001) {
        GoW0012 goW0012 = new GoW0012();
        goW001.setUpate_time(goW001.getNew_date());
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        BeanUtils.copyProperties(goW001, goW0012);
        w001Mapper.goW001_modify(goW001);
        goW001.setInput_money(goW001.getInput_money().subtract(goW001.getSetInputMoney()));
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select(goW0012);
        shared_method(list12, goW001, goW0012);
        w001Mapper.goW0012_modify(goW0012);
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> goW001Search(String[] goW001DatePickersArray) {
        ArrayList<Object> alo = new ArrayList<>();
        ArrayList<String> NewDatelist = new ArrayList<>();
        String params0 = goW001DatePickersArray[0];
        String params1 = goW001DatePickersArray[1];
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select_pickers(params0, params1);
        list12.forEach(entry -> NewDatelist.add(entry.getNew_date_Format()));
        // 值前後對調
        Collections.reverse(NewDatelist);
        ArrayList<GoW001> list1 = w001Mapper.goW0012_select_NewDatelist(NewDatelist);
        alo.add(list1);
        alo.add(list12);
        return alo;
    }
}
