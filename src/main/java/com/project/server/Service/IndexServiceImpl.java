package com.project.server.Service;

import com.project.server.Model.A;
import com.project.server.Model.B;
import com.project.server.mapper.IndexMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IndexMapper indexMapper;

    @Override
    public List<B> ins(Map<String, String> mapA) {
        logger.info("新增資料: {}", "*************** ins Start ***************");
        //Test (種類:食物) (順序:1)
        mapA.put("details", (mapA.get("details")
                + " (" + "種類" + ":" +
                mapA.get("radioItems")
                + ")" + " (" + "順序" + ":" +
                mapA.get("radio_group_value")
                + ")").trim()
        );
        indexMapper.insA(mapA);
        List<A> A = indexMapper.selectA(mapA.get("date"));
        Integer AexM = 0;
        Integer BinM = 0;
        for (A a : A) {
            switch (a.getExpense_and_income_number()) {
                case "A" -> AexM += a.getInputMoney();
                case "B" -> BinM += a.getInputMoney();
            }
        }

        List<B> B = indexMapper.selectB(mapA.get("date"));
        if (B.isEmpty()) {
            Map<String, String> mapB = new HashMap<>();
            mapB.put("date", mapA.get("date"));
            indexMapper.insB(mapB);
        }
        indexMapper.updateData(mapA.get("date"), AexM, BinM);
        return indexMapper.selectB(mapA.get("date"));
    }

    @Override
    public List<B> fin(Map<String, String> params) {
        logger.info("單一日期查詢===>Table B: {}", "*************** fin Start ***************");
        return indexMapper.selectB(params.get("data"));
    }

    @Override
    public List<A> finA(Map<String, String> params) {
        logger.info("單一日期查詢===>Table A: {}", "*************** finA Start ***************");
        return indexMapper.selectA(params.get("data"));
    }

    @Override
    public List<B> find(Map<String, String[]> params) {
        logger.info("Start~End日期查詢: {}", "*************** find Start ***************");
        Map<String, String> map = new HashMap<>();
        map.put("DatePickerStart", params.get("data")[0]);
        map.put("DatePickerEnd", params.get("data")[1]);
        return indexMapper.find(map);
    }

    @Override
    public List<A> findA(Map<String, List<Map<String, Object>>> params) {
        logger.info("查詢資料明細: {}", "*************** findA Start ***************");
        List<String> list = new ArrayList<>();
        params.forEach((key, value) -> value.forEach(e -> list.add(e.get("date").toString())));
        return indexMapper.findA(list);
    }

    @Override
    public List<A> del(Map<String, String> params) {
        logger.info("刪除資料明細: {}", "*************** del Start ***************");
        List<B> B = indexMapper.selectB(params.get("date"));
        int AexM = 0;
        int BinM = 0;
        for (B b : B) {
            switch (params.get("expense_and_income_number")) {
                case "A" -> {
                    AexM = b.getExpense() - Integer.parseInt(params.get("inputMoney"));
                    BinM = b.getIncome();
                }
                case "B" -> {
                    AexM = b.getExpense();
                    BinM = b.getIncome() - Integer.parseInt(params.get("inputMoney"));
                }
            }
        }
        indexMapper.del(params);
        indexMapper.updateData(params.get("date"), AexM, BinM);
        return indexMapper.selectA(params.get("date"));
    }

    @Override
    public List<A> enter(Map<String, String> params) {
        logger.info("修改資料明細: {}", "*************** enter Start ***************");
        Map<String, String> map = new HashMap<>();
        List<B> B = indexMapper.selectB(params.get("date"));
        int AexM = 0;
        int BinM = 0;
        int X = Integer.parseInt(params.get("setInputMoney"));
        int Y = Integer.parseInt(params.get("inputMoney"));
        for (B b : B) {
            switch (params.get("expense_and_income_number")) {
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
        indexMapper.updateData(params.get("date"), AexM, BinM);
        map.put("a_id", params.get("a_id"));
        map.put("inputMoney", params.get("setInputMoney"));
        map.put("details", params.get("details"));
        indexMapper.setUpdate(map);
        return indexMapper.selectA(params.get("date"));
    }


}
