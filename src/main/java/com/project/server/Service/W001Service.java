package com.project.server.Service;

import com.project.server.Entity.W001Bean;
import com.project.server.Entity.W001TypeBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface W001Service {
    ResponseEntity<String> submitUpload(W001Bean w001Bean);

    ResponseEntity<String> submitUploadOk(W001Bean w001Bean);

    ResponseEntity<String> submitForm(W001Bean w001Bean);

    ResponseEntity<String> submitFormOk(W001Bean w001Bean);

    List<Object> queryForm(W001Bean w001Bean);

    List<Object> modify(W001Bean w001Bean);

    List<Object> eventDelete(W001Bean w001Bean);

    List<Map<String, String>> w001type();

    List<Object> typeMethod1(W001TypeBean w001TypeBean);

    List<Object> typeMethod2(W001TypeBean w001TypeBean);

    List<Object> eventDeleteType(String typeName);

    List<Object> typeMethod(W001TypeBean w001TypeBean);
}
