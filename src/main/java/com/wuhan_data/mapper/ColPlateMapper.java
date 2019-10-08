package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.tools.DataSource;

public interface ColPlateMapper {
	@DataSource(value="dataSource_dm")
	public int add(ColPlate colPlate);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public int update(ColPlate colPlate);
	@DataSource(value="dataSource_dm")
	public List<ColPlate> getlist(Map map);//根据栏目id查找所有指标
	@DataSource(value="dataSource_dm")
	public int total(int cid);//查询数量
	@DataSource(value="dataSource_dm")
	public int updateShow(ColPlate colPlate);  //管理显示与否
	@DataSource(value="dataSource_dm")
	public int getAddPid(int cid); //得到需要添加的板块的id值
	@DataSource(value="dataSource_dm")
	public String getAddCname(int cid); //得到需要添加的板块所属栏目的名称
	@DataSource(value="dataSource_dm")
	public int updateWeight(Map map); //更新权重
	@DataSource(value="dataSource_dm")
	public int getPid(Map map); //得到pid
}
