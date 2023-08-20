package com.project.server.Service;

import com.project.server.LogUtils;
import com.project.server.Model.TableName;
import com.project.server.mapper.SetUpMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SetUpServiceImpl implements SetUpService {

    @Resource
    private SetUpMapper setUpMapper;

    @Override
    public List<String[]> readCsvData(String filePath) throws IOException {
        LogUtils.info("讀CSV檔", "*************** readCsvData Start ***************");
        List<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                data.add(fields);
            }
        }
        return data;
    }

    @Override
    public List<TableName> getTable() {
        LogUtils.info("查詢Table", "*************** getTable Start ***************");
        return setUpMapper.getTable();
    }
}
