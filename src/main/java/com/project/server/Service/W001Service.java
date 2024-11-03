package com.project.server.Service;

import com.project.server.Entity.W001Bean;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface W001Service {
    ResponseEntity<String> submitUpload(W001Bean w001Bean);

    ResponseEntity<String> submitUploadOk(W001Bean w001Bean);

    ResponseEntity<String> submitForm(W001Bean w001Bean);

    ResponseEntity<String> submitFormOk(W001Bean w001Bean);

    List<Object> queryForm(W001Bean w001Bean);
}
