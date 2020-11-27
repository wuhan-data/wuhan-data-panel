package com.wuhan_data.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.ColPlateIndiMapper;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.service.ColPlateIndiService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColPlateIndiServiceImpl implements ColPlateIndiService {

    @Autowired
    ColPlateIndiMapper colPlateIndiMapper;


    @Override
    public int add(ColPlateIndi colPlateIndi) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.add(colPlateIndi);
    }

    @Override
    public void delete(int id) {
        colPlateIndiMapper.delete(id);
    }

    @Override
    public int update(ColPlateIndi colPlateIndi) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.update(colPlateIndi);
    }

    @Override
    public List<ColPlateIndi> getlist(Map map) {
        List<ColPlateIndi> list = colPlateIndiMapper.getlist(map);
        Collections.sort(list);
        return list;
    }

    @Override
    public int total(int pid) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.total(pid);
    }

    @Override
    public int updateShow(ColPlateIndi colPlateIndi) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.updateShow(colPlateIndi);
    }

    @Override
    @Transactional
    public int updateWeight(int plateId, String[] ids) {
        int count = 0;
        try {
            for (int i = 0; i < ids.length; i++) {
                ColPlateIndi cpi = new ColPlateIndi();
                cpi.setPlate_id(plateId);
                cpi.setIndi_weight(i + 1);
                cpi.setIndi_id(Integer.valueOf(ids[i]));
                count += colPlateIndiMapper.updateWeight(cpi);
            }
        } catch (Exception e) {
            System.out.println("修改失败，异常信息：" + e.getMessage());
            return 0;
        }
        return count;
    }

    @Override
    public List<IndexManage> getAllIndi() {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.getAllIndi();
    }

    @Override
    public IndexManage getIndiInfo(String indi_code) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.getIndiInfo(indi_code);
    }

    @Override
    public String getPname(int pid) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.getPname(pid);
    }

    @Override
    public List<ColPlateIndi> searchIndi(String content) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.searchIndi(content);
    }

    @Override
    public String getIdAndNew_name(String indi_old_name) {
        // TODO Auto-generated method stub
        return colPlateIndiMapper.getIdAndNew_name(indi_old_name);
    }

}
