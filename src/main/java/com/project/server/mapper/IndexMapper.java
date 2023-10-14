package com.project.server.mapper;

import com.project.server.Model.A;
import com.project.server.Model.B;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {
    /**
     * <h3>index新增功能</h3>
     * <p>存A表</p>
     * @param map 前端fromData的值
     */
    void insA(Map<String,String> map);
    /**
     * <h3>index新增功能</h3>
     * <p>存B表</p>
     * @param map 前端fromData的值
     */
    void insB(Map<String,String> map);
    /**
     * <h3>index查詢功能</h3>
     * <p>查A表</p>
     * @param date 日期
     * @return 回傳資料庫Table A的值
     */
    List<A> selectA(String date);
    /**
     * <h3>index查詢功能</h3>
     * <p>查B表</p>
     * @param date 日期
     * @return 回傳資料庫Table B的值
     */
    List<B> selectB(String date);
    /**
     * <h3>index修改功能</h3>
     * @param date 日期
     * @param AexM 支出
     * @param BinM 收入
     */
    void updateData(String date,Integer AexM,Integer BinM);

    /**
     * <h3>index查詢功能</h3>
     * @param map Start日期、End日期
     * @return 回傳資料庫Table B的值
     */
    List<B> find(Map<String,String> map);
    /**
     * <h3>index查詢功能</h3>
     * @param listDate 前端A表功能多選日期
     * @return 回傳資料庫Table A的值
     */
    List<A> findA(List<String> listDate);

    /**
     * <h3>index刪除功能</h3>
     * @param a_id 前端fromData的值
     */
    void del(Map<String, String> a_id);

    /**
     * <h3>index重新計算修改功能</h3>
     * @param map 前端fromData的值
     */
    void setUpdate(Map<String, String> map);
}
