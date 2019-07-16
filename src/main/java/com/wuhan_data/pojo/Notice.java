package com.wuhan_data.pojo;

import java.util.Date;

public class Notice {
	private Integer id;
	private Integer sender_id;
	private Integer receiver_id;
	private String content;
	private Date create_time;
	private String title;
	private String remarks;
	private Integer is_read;
	
	public Integer getIs_read() {
		return is_read;
	}

	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getSender_id() {
		return sender_id;
	}

	public void setSender_id(Integer sender_id) {
		this.sender_id = sender_id;
	}

	public Integer getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(Integer receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", content=" + content
				+ ", create_time=" + create_time + ", title=" + title + ", remarks=" + remarks + ", is_read=" + is_read
				+ "]";
	}

	
	/*
	 * @Override public String toString() { return "Notice [id=" + id
	 * +",sender_id="+ sender_name+",receiver_id="+receiver_name + ",content="
	 * +content +",create_time="+create_time+"]"; }
	 */

}
