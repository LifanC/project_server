package com.project.server.Service;

import com.project.server.LogUtils;
import com.project.server.Model.A;
import com.project.server.Model.B;
import com.project.server.mapper.IndexMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexMapper indexMapper;

    @Override
    public List<B> ins(Map<String, Map<String, String>> params) {
        LogUtils.info("新增資料", "*************** ins Start ***************");
        Map<String, Object> mapA = new HashMap<>();
        params.forEach((key, value) -> mapA.putAll(value));
        //Test (種類:食物) (順序:1)
        mapA.put("details", mapA.get("details").toString()
                + " (" + "種類" + ":" +
                mapA.get("radioItems").toString()
                + ")" + " (" + "順序" + ":" +
                mapA.get("radio_group_value").toString()
                + ")"
        );
        indexMapper.insA(mapA);
        List<A> A = indexMapper.selectA(mapA.get("date").toString());
        Integer AexM = 0;
        Integer BinM = 0;
        for (A a : A) {
            switch (a.getExpense_and_income_number()) {
                case "A" -> AexM += a.getInputMoney();
                case "B" -> BinM += a.getInputMoney();
            }
        }

        List<B> B = indexMapper.selectB(mapA.get("date").toString());
        if (B.isEmpty()) {
            Map<String, Object> mapB = new HashMap<>();
            mapB.put("date", mapA.get("date"));
            indexMapper.insB(mapB);
        }
        indexMapper.updateData(mapA.get("date").toString(), AexM, BinM);
        return indexMapper.selectB(mapA.get("date").toString());
    }

    @Override
    public List<B> fin(Map<String, Object> params) {
        LogUtils.info("單一日期查詢===>Table B", "*************** fin Start ***************");
        return indexMapper.selectB(params.get("date").toString());
    }

    @Override
    public List<A> finA(Map<String, Object> params) {
        LogUtils.info("單一日期查詢===>Table A", "*************** finA Start ***************");
        return indexMapper.selectA(params.get("date").toString());
    }

    @Override
    public List<B> find(Map<String, String[]> params) {
        LogUtils.info("Start~End日期查詢", "*************** find Start ***************");
        Map<String, String> map = new HashMap<>();
        map.put("DatePickerStart", params.get("dateRange")[0]);
        map.put("DatePickerEnd", params.get("dateRange")[1]);
        return indexMapper.find(map);
    }

    @Override
    public List<A> findA(List<Object> params) {
        LogUtils.info("查詢資料明細", "*************** findA Start ***************");
        List<?> list = (List<?>) params.get(0);
        List<String> listDate = new ArrayList<>();
        for (Object o : list) {
            Map<?, ?> m = (Map<?, ?>) o;
            listDate.add(m.get("date").toString());
        }
        return indexMapper.findA(listDate);
    }

    @Override
    public List<A> del(Map<String, Object> params) {
        LogUtils.info("刪除資料明細", "*************** del Start ***************");
        List<B> B = indexMapper.selectB(params.get("date").toString());
        int AexM = 0;
        int BinM = 0;
        for (B b : B) {
            switch (params.get("expense_and_income_number").toString()) {
                case "A" -> {
                    AexM = b.getExpense() - Integer.parseInt(params.get("inputMoney").toString());
                    BinM = b.getIncome();
                }
                case "B" -> {
                    AexM = b.getExpense();
                    BinM = b.getIncome() - Integer.parseInt(params.get("inputMoney").toString());
                }
            }
        }
        indexMapper.del(params);
        indexMapper.updateData(params.get("date").toString(), AexM, BinM);
        return indexMapper.selectA(params.get("date").toString());
    }

    @Override
    public List<A> enter(Map<String, Object> params) {
        LogUtils.info("修改資料明細", "*************** enter Start ***************");
        Map<String, Object> map = new HashMap<>();
        List<B> B = indexMapper.selectB(params.get("date").toString());
        int AexM = 0;
        int BinM = 0;
        int X = Integer.parseInt(params.get("setInputMoney").toString());
        int Y = Integer.parseInt(params.get("inputMoney").toString());
        for (B b : B) {
            switch (params.get("expense_and_income_number").toString()) {
                case "A" -> {
                    AexM = b.getExpense() + (X - Y);
                    BinM = b.getIncome();
                }
                case "B" -> {
                    AexM = b.getExpense();
                    BinM = b.getIncome() + (X - Y);
                }
            }
        }
        indexMapper.updateData(params.get("date").toString(), AexM, BinM);
        map.put("a_id", params.get("a_id"));
        map.put("inputMoney", params.get("setInputMoney"));
        map.put("details", params.get("details"));
        indexMapper.setUpdate(map);
        return indexMapper.selectA(params.get("date").toString());
    }



}
