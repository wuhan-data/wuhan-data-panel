package com.wuhan_data.pojo;

public class Plate {
	int indexId;
	String indexName;
	public int getIndexId() {
		return indexId;
	}
	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Plate(int indexId, String indexName) {
		super();
		this.indexId = indexId;
		this.indexName = indexName;
	}
	
	

}
