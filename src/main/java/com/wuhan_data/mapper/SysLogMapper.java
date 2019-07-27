package com.wuhan_data.mapper;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.tools.DataSource;
public interface SysLogMapper {

	@DataSource(value="dataSource_dm")
    public int add(SysLog sysLog); 
	@DataSource(value="dataSource_dm")  
    public void delete(int id); 
	@DataSource(value="dataSource_dm")  
    public SysLog get(int id); 
	@DataSource(value="dataSource_dm")
    public int update(SysLog sysLog);  
	@DataSource(value="dataSource_dm")  
    public List<SysLog> list();
	@DataSource(value="dataSource_dm")
    public int count(); 
	//模糊查询，可分页
	@DataSource(value="dataSource_dm")
	public List<SysLog> search(Map<String,Object> parameter);
	//模糊查询数量
	@DataSource(value="dataSource_dm")
	public int searchCount(Map<String,Object> parameter);
	//列表，可分页
	@DataSource(value="dataSource_dm")
	public List<SysLog> listByPage(Map<String,Object> parameter);
}
