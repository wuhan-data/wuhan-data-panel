package com.wuhan_data.pojo;

import java.util.Date;

//错误日志
public class ErrorLog {
	Integer id;//错误日志id
	String func;//功能模块
	String code;//错误代码
	String msg;//错误信息
	String data;//报错数据
	String remark;//备注
	Date create_time;//创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "ErrorLog [id=" + id + ", func=" + func + ", code=" + code + ", msg=" + msg + ", data=" + data
				+ ", remark=" + remark + ", create_time=" + create_time + "]";
	}

}
