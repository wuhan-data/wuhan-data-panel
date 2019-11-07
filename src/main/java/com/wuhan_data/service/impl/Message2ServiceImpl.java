package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.AdminMapper;
import com.wuhan_data.mapper.Message2Mapper;
import com.wuhan_data.mapper.UserMapper;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Message2;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.service.Message2Service;

@Service
public class Message2ServiceImpl implements Message2Service {

	@Autowired
	Message2Mapper message2Mapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	AdminMapper adminMapper;
	@Override
	public int add(Message2 message2) {
		// TODO Auto-generated method stub
		return message2Mapper.add(message2);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		message2Mapper.delete(id);
	}

	@Override
	public Message2 get(int id) {
		// TODO Auto-generated method stub
		return message2Mapper.get(id);
	}

	@Override
	public int update(Message2 message2) {
		// TODO Auto-generated method stub
		return message2Mapper.update(message2);
	}

	@Override
	public List<Message2> list() {
		// TODO Auto-generated method stub
		return message2Mapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return message2Mapper.count();
	}

	@Override
	public List<Message2> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.listByPage(parameter);
	}

	@Override
	public List<Message2> searchByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.searchByContent(parameter);
	}

	@Override
	public int searchCountByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.searchCountByContent(parameter);
	}

	@Override
	public List<Message2> getUserNameList(List<Message2> message2s) {
		// TODO Auto-generated method stub
		for (int j=0;j<message2s.size();j++)
		{
			Message2 message2=message2s.get(j);
			String userIdList=message2.getReceiver_id();
			String idList=userIdList;
			String[] ids=idList.split("\\|");
			String[] names=new String[ids.length];
			for(int i=0;i<ids.length;i++)
			{
				if(ids[i].equals("")| ids[i]==null)
				{
					continue;
				}
				else {
					if(userMapper.get(Integer.valueOf(ids[i]))!=null)
					{
						User user=userMapper.get(Integer.valueOf(ids[i]));
						names[i]=user.getReal_name();
					}
				}
				
			}
			String result=StringUtils.join(names,"|");
			result=result+"|";
			message2.setReceiver_name(result);
			int adminId=message2.getSender_id();
			Admin admin=adminMapper.get(adminId);
			message2.setSender_name(admin.getUsername());
			
		}
		return message2s;
	}
}
