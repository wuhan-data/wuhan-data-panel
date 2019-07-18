package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.List;

import com.wuhan_data.app.showType.pojo.TableEntity;

public class TableType {
	
	public TableEntity getTable(String id,String title,List<List> dataX,List legendData,List<List> dataV) {
		//加入table类型
				List table=new ArrayList();
				List legendDATA=new ArrayList(legendData);
				legendDATA.add(0," ");
				table.add(legendDATA);
				for(int i=0;i<dataX.get(0).size();i++) {
					List listData=new ArrayList();
					listData.add(dataX.get(0).get(i));
					
					for(int j=0;j<dataX.size();j++) {
						listData.add(dataV.get(j).get(i));
					}
					table.add(listData);
					
				}
				String tableId=id+"table";
				TableEntity tableEntity=new TableEntity(tableId,title,table);
				return tableEntity;
	}

}
