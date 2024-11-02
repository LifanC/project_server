package com.project.server.Service;

import com.project.server.Entity.W001Bean;
import org.springframework.http.ResponseEntity;

public interface W001Service {
    ResponseEntity<String> submitUpload(W001Bean w001Bean);

    ResponseEntity<String> submitForm(W001Bean w001Bean);

    ResponseEntity<String> submitFormOk(W001Bean w001Bean);
}
