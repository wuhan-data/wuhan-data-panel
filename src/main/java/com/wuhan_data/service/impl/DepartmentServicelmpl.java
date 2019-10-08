package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.DepartmentMapper;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.DepartmentService;

@Service
public class DepartmentServicelmpl implements DepartmentService {
	
	@Autowired
	DepartmentMapper departmentMapper;

	@Override
	public int add(Department department) {
		// TODO Auto-generated method stub
		return departmentMapper.add(department);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		departmentMapper.delete(id);

	}

	@Override
	public Department get(int id) {
		// TODO Auto-generated method stub
		return departmentMapper.get(id);
	}

	@Override
	public int update(Department department) {
		// TODO Auto-generated method stub
		return departmentMapper.update(department);
	}

	@Override
	public List<Department> list() {
		// TODO Auto-generated method stub
		return departmentMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return departmentMapper.count();
	}
	
	//列表，可分页
	@Override
	public List<Department> listByPage(Map<String,Object> parameter){
		return departmentMapper.listByPage(parameter);
	}
	@Override
	public List<Department> search(Map<String,Object> parameter){
		return departmentMapper.search(parameter);
	}
	@Override
	public int searchCount(Map<String,Object> parameter) {
		return departmentMapper.searchCount(parameter);
	}

	@Override
	public List<Department> getByCode(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return departmentMapper.getByCode(parameter);
	}

	@Override
	public List<Department> getByName(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return departmentMapper.getByName(parameter);
	}

	@Override
	public String getNameList(String idList) {
		// TODO Auto-generated method stub
		String[] ids=idList.split("\\|");
		String[] names=new String[ids.length];
		for(int i=0;i<ids.length;i++)
		{
			Department department=departmentMapper.get(Integer.valueOf(ids[i]));
			names[i]=department.getDepartment_name();
		}
		String result=StringUtils.join(names,"|");
		return result;
	}

}
