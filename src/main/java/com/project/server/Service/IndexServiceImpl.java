package com.project.server.Service;

import com.project.server.Entity.IndexUrlBean;
import com.project.server.Mapper.IndexMapper;
import com.project.server.Model.IndexUrl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    @Override
    public List<Map<String, Object>> indexUrlDefault() {
        return indexMapper.getPermissions();
    }

    @Override
    public boolean[] login(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name())
                || StringUtils.isBlank(indexUrlBean.getNumber())
                || StringUtils.isBlank(indexUrlBean.getPermissions_value())) {
            return new boolean[]{false, false};
        }
        ArrayList<IndexUrl> list = indexMapper.select(indexUrlBean);
        return (CollectionUtils.isEmpty(list)) ? new boolean[]{false, false} : new boolean[]{true, true};
    }

    @Override
    public boolean[] register(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name())
                || StringUtils.isBlank(indexUrlBean.getNumber())
                || StringUtils.isBlank(indexUrlBean.getPermissions_value())) {
            return new boolean[]{false, false};
        }
        boolean[] result;
        Instant now = Instant.now();
        ArrayList<IndexUrl> list = indexMapper.select(indexUrlBean);
        boolean isEmptyList = CollectionUtils.isEmpty(list);
        result = (isEmptyList) ? new boolean[]{true, true} : new boolean[]{true, false};
        if (isEmptyList) {
            indexUrlBean.setUpate_time(Date.from(now));
            indexMapper.insert(indexUrlBean);
        }
        return result;
    }

    @Override
    public boolean[] delete(IndexUrlBean indexUrlBean) {
        if (StringUtils.isBlank(indexUrlBean.getF_name())
                || StringUtils.isBlank(indexUrlBean.getNumber())
                || StringUtils.isBlank(indexUrlBean.getPermissions_value())) {
            return new boolean[]{false, false};
        }

        boolean deleteList = indexMapper.delete(indexUrlBean) == 1;
        if (deleteList) {
            indexMapper.deleteW001(indexUrlBean);
            indexMapper.deleteW0012(indexUrlBean);
            indexMapper.deleteW002(indexUrlBean);
            return new boolean[]{true, true};
        } else {
            return new boolean[]{false, false};
        }
    }

    @Override
    public List<IndexUrl> indexUrlPermissions(IndexUrlBean indexUrlBean) {
        return indexMapper.select(indexUrlBean);
    }

    private Map<String, String> common(String paramsAccount, String paramsPassword) {
        Map<String, String> privatedata = new HashMap<>();
        privatedata.put("paramsAccount", paramsAccount);
        privatedata.put("paramsPassword", paramsPassword);
        return privatedata;
    }

    @Override
    public Map<String, ?> permissionsFunctionSelect(String paramsAccount, String paramsPassword) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = indexMapper.selectPrivatedata(common(paramsAccount, paramsPassword));
        map.put("isNotEmpty", CollectionUtils.isNotEmpty(list));
        List<Map<String, Object>> list2 = indexMapper.selectEncryptionPrivatedata();
        map.put("selectEncryption", list2);
        return map;
    }

    @Override
    public boolean permissionsFunctionAdd(String paramsAccount, String paramsPassword) {
        List<Map<String, Object>> list = indexMapper.selectPrivatedata(common(paramsAccount, paramsPassword));
        if (CollectionUtils.isNotEmpty(list)) {
            return false;
        }
        indexMapper.insertPrivatedata(common(paramsAccount, paramsPassword));
        Map<String, String> encryptionprivatedata = new HashMap<>();
        encryptionprivatedata.put("paramsAccountEncryption", paramsAccount.substring(0, 4) + "*****" + paramsAccount.charAt(9));
        encryptionprivatedata.put("paramsPasswordEncryption", paramsPassword.substring(0, 4) + "*****" + paramsPassword.charAt(9));
        indexMapper.insertEncryptionPrivatedata(encryptionprivatedata);
        return true;
    }

    @Override
    public List<Map<String, Object>> permissionsFunctionSelectAdmin() {
        return indexMapper.selectPrivatedataAll();
    }

}
