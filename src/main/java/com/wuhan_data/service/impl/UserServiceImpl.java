package com.wuhan_data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.mapper.UserMapper;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.StringToMap;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserServiceApp userServiceApp;
	@Autowired
    UserMapper userMapper;
	@Autowired
	RoleService roleService;
     
    public List<User> list(){
        return userMapper.list();
    }

	@Override
	public User logincheck(User user) {
		User u = userMapper.logincheck(user);
		
		return u;
	}

	@Override
	public void regist(User user) {
		userMapper.register(user);
		
	}

@Override
	public int add(User user) {
		return userMapper.add(user);
	}
	@Override
	public void delete(int id) {
		userMapper.delete(id);
	}
	@Override
	public User get(int id) {
		return userMapper.get(id);
	}
	@Override
	public int updata(User user) {
		return userMapper.update(user);
	}
	@Override
	public int count() {
		return userMapper.count();
	}
	
	@Override
	public List<User> search(Map<String,Object> parameter){
		return userMapper.search(parameter);
	}
	@Override
	public int searchCount(Map<String,Object> parameter) {
		return userMapper.searchCount(parameter);
	}
	
	
	@Override
	public List<User> listByPage(Map<String,Object> parameter){
		return userMapper.listByPage(parameter);
		
	}

	@Override
	public User getByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.getByName(username);
	}

	@Override
	public List<User> getByRole(int role_id) {
		// TODO Auto-generated method stub
		String role_idString=String.valueOf(role_id);
		List<User> allUsers=userMapper.list();
		List<User> users=new ArrayList<User>();
		for (int i=0;i<allUsers.size();i++)
		{
			User user=allUsers.get(i);
			String[] roles=user.getRole_id().split("\\|");
			if (Arrays.asList(roles).contains(role_idString))
			{
				users.add(user);
			}
			
		}
		return users;
	}

	@Override
	public List<User> searchByRealname(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapper.searchByRealname(parameter);
	}

	@Override
	public List<User> getByTel(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapper.getByTel(parameter);
	}

	@Override
	public Map<String, List<String>> getAllPower(int id) {
		// TODO Auto-generated method stub
		Map<String, List<String>> data = new HashMap<String, List<String>>();
  		User user = userServiceApp.get(id);
  		
  		String roles=user.getRole_id();
  		
  		String[] roleList=roles.split("\\|");
  		
  		List<String> power_1String=new ArrayList<String>();
  		List<String> power_2String=new ArrayList<String>();
  		List<String> power_3String=new ArrayList<String>();
  		List<String> power_4String=new ArrayList<String>();
  		//遍历每一个角色的权限
  		for(int i=0;i<roleList.length;i++)
  		{
  			String role_nameString=roleList[i];
  			Role role=roleService.get(Integer.valueOf(role_nameString));
  			if(role==null)
  				continue;
  			power_1String.add(role.getRole_power_1());
  			power_2String.add(role.getRole_power_2());
  			power_3String.add(role.getRole_power_3());	
  			power_4String.add(role.getRole_power_4());	
  		}
  		
  		Set <String> power_1=new HashSet<String>();
  		for (int i=0;i<power_1String.size();i++)
  		{
  			if (power_1String.get(i)==null |power_1String.get(i)=="")
				{continue;}
  			String a=power_1String.get(i);
  			String[] alist=a.split("\\|");
  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
  			power_1.addAll(set);	
  		}
  		System.out.println("power_1"+power_1.toString());
  		Set <String> power_2=new HashSet<String>();
  		for (int i=0;i<power_2String.size();i++)
  		{
  			if (power_2String.get(i)==null |power_2String.get(i)=="")
				{continue;}
  			String a=power_2String.get(i);
  			String[] alist=a.split("\\|");
  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
  			power_2.addAll(set);	
  		}
  		System.out.println("power_2"+power_2.toString());
  		Set <String> power_3=new HashSet<String>();
  		for (int i=0;i<power_3String.size();i++)
  		{
  			if (power_3String.get(i)==null |power_3String.get(i)=="")
				{continue;}
  			String a=power_3String.get(i);
  			String[] alist=a.split("\\|");
  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
  			power_3.addAll(set);	
  		}
  		System.out.println("power_3"+power_3.toString());
  		Set <String> power_4=new HashSet<String>();
  		System.out.println("大小"+power_4String.size());
  		for (int i=0;i<power_4String.size();i++)
  		{
  			if (power_4String.get(i)==null |power_4String.get(i)=="")
  				{continue;}
  			String a=power_4String.get(i);
  			String[] alist=a.split("\\|");
  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
  			power_4.addAll(set);	
  		}
  		System.out.println("power_4"+power_4.toString());
  		List<String> powerThemes=new ArrayList<String>(power_1);
  		List<String> powerIndexSpecials=new ArrayList<String>(power_2);
  		List<String> powerIndexManages=new ArrayList<String>(power_3);
  		List<String> powerIndexManages2=new ArrayList<String>(power_4);
  		data.put("powerThemes", powerThemes);
  		data.put("powerIndexSpecials", powerIndexSpecials);
  		data.put("powerIndexManages", powerIndexManages);
  		data.put("powerIndexManages2", powerIndexManages2);
		return data;	
	}

	@Override
	public boolean isExistRoleId(int roleId) {
		// TODO Auto-generated method stub
		String roleIdList=String.valueOf(roleId);
		List<User> users=userMapper.list();
		for (int i=0;i<users.size();i++)
		{
			User user=users.get(i);
			String[] roleid=user.getRole_id().split("\\|");
			for (String s:roleid) {
				if (s.equals(roleIdList))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isExistDepartmentId(int departmentId) {
		// TODO Auto-generated method stub
		String departmentIdList=String.valueOf(departmentId);
		List<User> users=userMapper.list();
		for(int i=0;i<users.size();i++)
		{
			User user=users.get(i);
			String [] departmentid=user.getDepartment_id().split("\\|");
			for (String s:departmentid) {
				if (s.equals(departmentIdList))
					return true;
			}
		}
		return false;
	}
}
