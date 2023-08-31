package com.project.server.Service;


import java.io.IOException;
import java.util.List;

public interface SetUpService {

    List<String[]> readCsvData(String filePath) throws IOException;
}
