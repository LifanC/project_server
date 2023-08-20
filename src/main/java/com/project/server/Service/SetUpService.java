package com.project.server.Service;

import com.project.server.Model.TableName;


import java.io.IOException;
import java.util.List;

public interface SetUpService {

    List<String[]> readCsvData(String filePath) throws IOException;
    List<TableName> getTable();
}
