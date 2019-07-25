package com.wuhan_data.pojo;

import java.util.Date;

public class SessionSQL {
	private Integer id;
	private String sess_key;
	private String sess_value;
	private Date create_date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSess_key() {
		return sess_key;
	}
	public void setSess_key(String sess_key) {
		this.sess_key = sess_key;
	}
	public String getSess_value() {
		return sess_value;
	}
	public void setSess_value(String sess_value) {
		this.sess_value = sess_value;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "SessionSQL [id=" + id + ", sess_key=" + sess_key + ", sess_value=" + sess_value + ", create_date="
				+ create_date + "]";
	}



}
