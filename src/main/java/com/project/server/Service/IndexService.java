package com.project.server.Service;

import com.project.server.Model.A;
import com.project.server.Model.B;

import java.util.List;
import java.util.Map;


public interface IndexService {
    List<B> ins(Map<String, Map<String, String>> params);

    List<B> fin(Map<String, Object> params);

    List<A> finA(Map<String, Object> params);

    List<B> find(Map<String, String[]> params);

    List<A> findA(List<Object> params);

    List<A> del(Map<String, Object> params);

    List<A> enter(Map<String, Object> params);

}
