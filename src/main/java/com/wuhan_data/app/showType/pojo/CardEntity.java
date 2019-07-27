package com.wuhan_data.app.showType.pojo;

public class CardEntity {
	String id;
	String classTitle;
	String classType;
	String cardText;
		
	public CardEntity(String id, String classTitle, String classType, String cardText) {
		super();
		this.id = id;
		this.classTitle = classTitle;
		this.classType = classType;
		this.cardText = cardText;
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
	public String getCardText() {
		return cardText;
	}
	public void setCardText(String cardText) {
		this.cardText = cardText;
	}
	
	
}
