package com.wuhan_data.app.showType;

import java.util.ArrayList;
import java.util.List;

import com.wuhan_data.app.showType.pojo.TableEntity;

public class TableType {

	public TableEntity getTable(String id, String title, List<List<String>> dataX, List<String> legendData,
			List<List<String>> dataV) {
		try {
			// 预处理数据，合并1月及2月的数据
			int ignoreX = -1;
			for (int i = 0; i < dataX.get(0).size(); i++) {
				if (dataX.get(0).get(i).length() != 7) {
					continue;
				}
				String monthString = dataX.get(0).get(i).toString().substring(5, 7);
				if (monthString.equals("01")) {
					for (int j = 0; j < dataV.size(); j++) {
						if (dataV.get(j).get(i) == null || dataV.get(j).get(i).toString().equals("")) {
							// 存在一月无数据情况，需要进行合并
							ignoreX = i;
						}
					}
				}
			}
			// 删除1月的空数据
			if (ignoreX != -1) {
				// 处理x轴数据
				List<String> dataX1 = new ArrayList<String>(dataX.get(0));
				dataX1.remove(ignoreX);
				String dataXString = dataX1.get(ignoreX).substring(0, 5) + "1-2";
				dataX1.set(ignoreX, dataXString);
				dataX.set(0, dataX1);
				// 处理数据值
				List<List<String>> dataV1 = new ArrayList<List<String>>();
				for (int i = 0; i < dataV.size(); i++) {
					List<String> tempList = new ArrayList<String>(dataV.get(i));
					tempList.remove(ignoreX);
					dataV1.add(tempList);
				}
				dataV = dataV1;
			}
		} catch (Exception e) {
			System.out.println("处理表格数据合并出错");
		}

		// 加入table类型
		if (dataX.size() == 0) {
			List legendDATA = new ArrayList(legendData);
			legendDATA.add(0, " ");

		}
		List legendDATA = new ArrayList(legendData);
		legendDATA.add(0, " ");
		List table = new ArrayList();
		table.add(legendDATA);
		for (int i = 0; i < dataX.get(0).size(); i++) {
			List listData = new ArrayList();
			listData.add(dataX.get(0).get(i));
			for (int j = 0; j < dataX.size(); j++) {
				listData.add(dataV.get(j).get(i));
			}
			table.add(listData);

		}
		String tableId = id + "table";
		TableEntity tableEntity = new TableEntity(tableId, title, table);
		return tableEntity;
	}

}
