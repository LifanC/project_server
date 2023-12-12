package com.project.server.Service;

import com.project.server.Model.A;
import com.project.server.Model.B;
import com.project.server.mapper.IndexMapper;
import jakarta.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    /**
     * <h3>index新增功能</h3>
     * @param mapA 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @Override
    public List<B> ins(Map<String, String> mapA) {
        mapA.put("details", (mapA.get("details")+
                " (種類:"+mapA.get("radioItems")+
                ") (順序:" +mapA.get("radio_group_value")+
                ")").trim());
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
        if (CollectionUtils.isEmpty(B)) {
            Map<String, String> mapB = new HashMap<>();
            mapB.put("date", mapA.get("date"));
            indexMapper.insB(mapB);
        }
        indexMapper.updateData(mapA.get("date"), AexM, BinM);
        return indexMapper.selectB(mapA.get("date"));
    }

    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @Override
    public List<B> fin(Map<String, String> params) {
        return indexMapper.selectB(params.get("data"));
    }

    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @Override
    public List<A> finA(Map<String, String> params) {
        return indexMapper.selectA(params.get("data"));
    }

    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    @Override
    public List<B> find(Map<String, String[]> params) {
        Map<String, String> map = new HashMap<>();
        map.put("DatePickerStart", params.get("data")[0]);
        map.put("DatePickerEnd", params.get("data")[1]);
        return indexMapper.find(map);
    }

    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @Override
    public List<A> findA(Map<String, List<Map<String, Object>>> params) {
        List<String> list = new ArrayList<>();
        params.forEach((key, value) -> value.forEach(e -> list.add(e.get("date").toString())));
        return indexMapper.findA(list);
    }

    /**
     * <h3>index刪除功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @Override
    public List<A> del(Map<String, String> params) {
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

    /**
     * <h3>index修改資料明細功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    @Override
    public List<A> enter(Map<String, String> params) {
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
