package com.project.server.Service;

import com.project.server.Model.A;
import com.project.server.Model.B;
import java.util.List;
import java.util.Map;


public interface IndexService {
    List<B> ins(Map<String, String> params);

    List<B> fin(Map<String, String> params);

    List<A> finA(Map<String, String> params);

    List<B> find(Map<String, String[]> params);

    List<A> findA(List<Object> params);

    List<A> del(Map<String, String> params);

    List<A> enter(Map<String, String> params);

}
