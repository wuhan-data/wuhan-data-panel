package com.wuhan_data.pojo;
import java.util.Date;
public class Message {
	private Integer id;
	private Integer sender_id;
	private Integer receiver_id;
	private String title;
	private String url;
	private String remarks;
	private Date create_time;
	private Integer is_read;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", title=" + title
				+ ", url=" + url + ", remarks=" + remarks + ", create_time=" + create_time + ", is_read=" + is_read
				+ "]";
	}
	
	

}
