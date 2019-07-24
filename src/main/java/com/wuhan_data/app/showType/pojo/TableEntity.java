package com.wuhan_data.app.showType.pojo;

import java.util.List;

public class TableEntity {
	String id;
	String classTitle;
	String classType="table";
	String classHeight="300";
	
	public String getClassHeight() {
		return classHeight;
	}
	public void setClassHeight(String classHeight) {
		this.classHeight = classHeight;
	}
	List tableBody;
	public TableEntity() {
		super();
	}
	public TableEntity(String id,String classTitle,List tableBody) {
		this.id=id;
		this.classTitle=classTitle;
		this.tableBody=tableBody;
		
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
	public void setClassType(String calssType) {
		this.classType = classType;
	}
	public List getTableBody() {
		return tableBody;
	}
	public void setTableBody(List tableBody) {
		this.tableBody = tableBody;
	}
	

}
