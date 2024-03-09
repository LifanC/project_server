package com.project.server.Service.GoW001;

import com.project.server.Entity.GoW001Bean;
import com.project.server.Entity.GoW0012Bean;
import com.project.server.Mapper.W001Mapper;
import com.project.server.Model.GoW001;
import com.project.server.Model.GoW0012;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class W001ServiceImpl implements W001Service {
    @Resource
    private W001Mapper w001Mapper;

    private String DateFormat(Date newDate) {
        return newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    private ArrayList<Object> printTheData(GoW001Bean goW001, GoW0012Bean goW0012) {
        return new ArrayList<>(List.of(w001Mapper.goW001_select(goW001), w001Mapper.goW0012_select(goW0012)));

    }

    private void shared_method(ArrayList<GoW0012> list12, GoW001Bean goW001, GoW0012Bean goW0012) {
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

    private Map<Integer, ArrayList<BigDecimal>> shared_proportion(ArrayList<GoW001> list1, Map<Integer, ArrayList<BigDecimal>> result, int[] targetValues) {
        for (int target : targetValues) {
            List<BigDecimal> targetList = list1.stream().filter(a -> Integer.parseInt(a.getRadio_group_value()) == target).map(GoW001::getInput_money).toList();
            BigDecimal sum = targetList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            result.put(target, new ArrayList<>(List.of(sum)));
        }
        return result;
    }

    // 500{單項支出金額}/(除)1000{支出總額}=0.5(占比%數)*100 =>公式 Ex:50%
    private BigDecimal calculateProportion(BigDecimal value, Map<Integer, ArrayList<BigDecimal>> result, Integer key) {
        BigDecimal r = new BigDecimal(result.get(key).get(0).toString());
        return (r.divide(value, MathContext.DECIMAL32).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public ArrayList<Object> goW001Add(GoW001Bean goW001) {
        GoW0012Bean goW0012 = new GoW0012Bean();
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
    public ArrayList<Object> goW001Single_search(GoW001Bean goW001) {
        GoW0012Bean goW0012 = new GoW0012Bean();
        goW001.setNew_date_Format(DateFormat(goW001.getNew_date()));
        BeanUtils.copyProperties(goW001, goW0012);
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> W001UrlDefault(String fName, String number) {
        GoW001Bean goW001 = new GoW001Bean();
        goW001.setF_name(fName);
        goW001.setNumber(number);
        GoW0012Bean goW0012 = new GoW0012Bean();
        BeanUtils.copyProperties(goW001, goW0012);
        return printTheData(goW001, goW0012);
    }


    @Override
    public ArrayList<Object> confirmEventDelete(Map<String, Object> params) {
        GoW001Bean goW001 = new GoW001Bean();
        GoW0012Bean goW0012 = new GoW0012Bean();
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
    public ArrayList<Object> goW001Modify(GoW001Bean goW001) {
        GoW0012Bean goW0012 = new GoW0012Bean();
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
    public ArrayList<Object> goW001Search(String[] combinedArray) {
        String params0 = combinedArray[0];
        String params1 = combinedArray[1];
        String params2 = combinedArray[2];
        String params3 = combinedArray[3];
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select_pickers(params0, params1, params2, params3);
        if (CollectionUtils.isEmpty(list12)) {
            return new ArrayList<>();
        }
        ArrayList<String> newDatelist = list12.stream().map(GoW0012::getNew_date_Format).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(newDatelist);
        ArrayList<GoW001> list1 = w001Mapper.goW001_select_NewDatelist(newDatelist, params2, params3);
        return new ArrayList<>(List.of(list1, list12));
    }

    @Override
    public ArrayList<Object> W001proportion(GoW0012Bean goW0012) {
        // 在需要使用的地方添加 @Builder 注解
        GoW001Bean goW001 = GoW001Bean.builder().f_name(goW0012.getF_name()).number(goW0012.getNumber()).new_date_Format(goW0012.getNew_date_Format()).build();
        ArrayList<GoW001> list1 = w001Mapper.goW001_select(goW001);
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select(goW0012);
        ArrayList<Object> all_result = new ArrayList<>();

        Map<Integer, ArrayList<BigDecimal>> result = new HashMap<>();
        int[] targetValues = {1, 2, 3, 4, 5, 6, 7};

        list12.forEach(a -> {
            Map<String, Object> s1_result = new HashMap<>();
            s1_result.put("income", a.getIncome());
            s1_result.put("expense", a.getExpense());
            s1_result.put("income_proportion", calculateProportion(a.getIncome(), shared_proportion(list1, result, targetValues), 1) + "%");
            for (int i = 2; i <= targetValues.length; i++) {
                String key = "expense_r" + i + "_proportion";
                s1_result.put(key, calculateProportion(a.getExpense(), shared_proportion(list1, result, targetValues), i) + "%");
            }
            all_result.add(s1_result);
        });
        return all_result;
    }

    @Override
    public ArrayList<Object> goW001proportionSingle_search(GoW001Bean goW001) {
        GoW0012Bean goW0012 = GoW0012Bean.builder().build();
        return printTheData(goW001, goW0012);
    }

    @Override
    public ArrayList<Object> goW001monthProportion(String[] combinedArray) {
        ArrayList<GoW0012> list12 = w001Mapper.goW0012_select_pickers(combinedArray[0], combinedArray[1], combinedArray[2], combinedArray[3]);
        ArrayList<String> newDatelist = list12.stream().map(GoW0012::getNew_date_Format).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(newDatelist);
        ArrayList<GoW001> list1 = w001Mapper.goW001_select_NewDatelist(newDatelist, combinedArray[2], combinedArray[3]);
        if (CollectionUtils.isEmpty(list12) && CollectionUtils.isEmpty(list1)) {
            return new ArrayList<>();
        }
        ArrayList<Object> all_result = new ArrayList<>();
        BigDecimal sumIncome = BigDecimal.ZERO;
        BigDecimal sumExpense = BigDecimal.ZERO;
        for (GoW0012 l12 : list12) {
            sumIncome = sumIncome.add(l12.getIncome());
            sumExpense = sumExpense.add(l12.getExpense());
        }
        Map<Integer, ArrayList<BigDecimal>> result = new HashMap<>();
        int[] targetValues = {1, 2, 3, 4, 5, 6, 7};
        Map<String, Object> s2_result = new HashMap<>();
        s2_result.put("income", sumIncome);
        s2_result.put("expense", sumExpense);
        s2_result.put("income_proportion", calculateProportion(sumIncome, shared_proportion(list1, result, targetValues), 1) + "%");
        for (int i = 2; i <= targetValues.length; i++) {
            String key = "expense_r" + i + "_proportion";
            s2_result.put(key, calculateProportion(sumExpense, shared_proportion(list1, result, targetValues), i) + "%");
        }
        all_result.add(s2_result);
        return all_result;
    }


}
