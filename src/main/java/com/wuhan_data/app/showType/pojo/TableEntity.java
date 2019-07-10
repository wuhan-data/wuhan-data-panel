package com.wuhan_data.app.showType.pojo;

import java.util.List;

//表格
public class TableEntity {
	
	String id;
	String classTitle;
	String classType="table";
	String classHeight="300";
	List<List<String>> tableBody;
	
	public TableEntity() {
		super();
	}
	public TableEntity(String id, String classTitle, List<List<String>> tableBody) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.tableBody = tableBody;
	}
	public TableEntity(String id, String classTitle, String classType, String classHeight, List<List<String>> tableBody) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.classType = classType;
		this.classHeight = classHeight;
		this.tableBody = tableBody;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassTitle() {
		return classTitle;
	}
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getClassHeight() {
		return classHeight;
	}
	public void setClassHeight(String classHeight) {
		this.classHeight = classHeight;
	}
	public List<List<String>> getTableBody() {
		return tableBody;
	}
	public void setTableBody(List<List<String>> tableBody) {
		this.tableBody = tableBody;
	}
	
	

}
