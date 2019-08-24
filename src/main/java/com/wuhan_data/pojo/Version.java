package com.wuhan_data.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Version {
	private Integer id;
	private String appid;
	private String platform;
	private String version;
	private String text;
	private String url;
	private Date create_time;
	private String timeString;
	public String getTimeString() {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		return sdf.format(create_time);
	}
	public void setTimeString() {
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		this.timeString = sdf.format(create_time);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Version [id=" + id + ", appid=" + appid + ", platform=" + platform + ", version=" + version + ", text="
				+ text + ", url=" + url + ", create_time=" + create_time + "]";
	}
	

}
