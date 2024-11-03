package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    private List<List<IndexUrl>> indexMethod(IndexUrlBean indexUrlBean) {
        List<IndexUrl> list = indexMapper.select(indexUrlBean);
        List<IndexUrl> listh = indexMapper.selecth(indexUrlBean);
        List<List<IndexUrl>> result = new ArrayList<>();
        result.add(list);
        result.add(listh);
        return result;
    }

    private String DateFormat(Date newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(formatter);
    }

    @Override
    public boolean login(IndexUrlBean indexUrlBean) {
        List<IndexUrl> list = indexMethod(indexUrlBean).get(0);
        return list.isEmpty();
    }

    @Override
    public List<Object> dialogAdmin() {
        IndexUrlBean indexUrlBean = IndexUrlBean.builder()
                .accountNumber("%")
                .password("%")
                .build();
        return new ArrayList<>(indexMethod(indexUrlBean));
    }

    @Override
    public List<Object> dialogVisibleMethod(IndexUrlBean indexUrlBean) {
        List<Object> result = new ArrayList<>();
        indexUrlBean.setUpdate_time(DateFormat(new Date()));
        switch (indexUrlBean.getDialogValue()) {
            case 1 -> {
                String maxNumber = indexMapper.maxSelect(indexUrlBean);
                try {
                    indexUrlBean.setDataNumber(String.format("%04d", Integer.parseInt(maxNumber) + 1));
                    indexMapper.create(indexUrlBean);
                    indexMapper.createh(indexUrlBean);
                    result.add("註冊成功");
                } catch (Exception ex) {
                    result.add("資料重複，註冊失敗");
                }
                indexUrlBean.setPassword("%");
                result.add(indexMethod(indexUrlBean));
            }
            case 2 -> {
                try {
                    int updateNum = indexMapper.update(indexUrlBean);
                    indexMapper.deleteW001(indexUrlBean);
                    indexMapper.deleteW001h(indexUrlBean);
                    if (updateNum != 0) {
                        indexMapper.createh(indexUrlBean);
                        result.add("更新成功");
                    } else {
                        result.add("無資料，更新失敗");
                    }
                    indexUrlBean.setPassword("%");
                } catch (Exception ex) {
                    result.add("更新失敗");
                }
                result.add(indexMethod(indexUrlBean));
            }
            case 3 -> {
                try {
                    int delNum = indexMapper.delete(indexUrlBean);
                    indexMapper.deleteW001(indexUrlBean);
                    indexMapper.deleteW001h(indexUrlBean);
                    if (delNum != 0) {
                        indexMapper.createh(indexUrlBean);
                        result.add("刪除成功");
                    } else {
                        result.add("無資料，刪除失敗");
                    }
                    indexUrlBean.setPassword("%");
                } catch (Exception ex) {
                    result.add("刪除失敗");
                }
                result.add(indexMethod(indexUrlBean));
            }
            default -> {
                List<List<IndexUrl>> list = indexMethod(indexUrlBean);
                if (list.isEmpty()) {
                    result.add("查詢失敗");
                } else {
                    result.add("查詢成功");
                }
                result.add(list);
            }
        }
        return result;
    }
}

