package com.project.server.Service;

import com.project.server.Model.A;
import com.project.server.Model.B;
import java.util.List;
import java.util.Map;


public interface IndexService {
    /**
     * <h3>index新增功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    List<B> ins(Map<String, String> params);
    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    List<B> fin(Map<String, String> params);
    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    List<A> finA(Map<String, String> params);
    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table B的值
     */
    List<B> find(Map<String, String[]> params);
    /**
     * <h3>index查詢功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    List<A> findA(Map<String, List<Map<String,Object>>> params);
    /**
     * <h3>index刪除功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    List<A> del(Map<String, String> params);
    /**
     * <h3>index修改資料明細功能</h3>
     * @param params 前端fromData的值
     * @return 回傳資料庫Table A的值
     */
    List<A> enter(Map<String, String> params);

}
