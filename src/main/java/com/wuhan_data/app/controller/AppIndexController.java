package com.wuhan_data.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.AppIndexService;
import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.AnalysisType;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;

@Controller
@RequestMapping("")
public class AppIndexController {
	@Autowired
	AppIndexService appIndexService;

	// 测试
	@RequestMapping(value = "t", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String t() {
		System.out.println("asd");
		Map map = new HashMap();
		map.put("code", 1);
		map.put("id", 123456);
		String param = JSON.toJSONString(map);
		return param;
	}

	// 轮播图
	@RequestMapping(value = "initIndexPicture", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String initIndexPicture() {
		List<IndexPic> indexList = appIndexService.getlist();
		String param = JSON.toJSONString(indexList);
		return param;
	}

	// 经济分析十个icon
	@RequestMapping(value = "initIndexIcon", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String initIndexIcon() {
		List<AnalysisType> indexList = appIndexService.getIconList();
		String param = JSON.toJSONString(indexList);
		return param;
	}

	// 首页的专题
	@RequestMapping(value = "initIndexTopic", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String initIndexTopic(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, UnknownHostException {
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		String ip = InetAddress.getLocalHost().getHostAddress() + ":" + request.getLocalPort();
		List<IndexSpecial> indexList = appIndexService.getIndexSpecialList();
		for (int i = 0; i < indexList.size(); i++) {
			String hostIP = indexList.get(i).getImage();
			hostIP = hostIP.replace("http://", "");// 去除http和https前缀
			String[] arr = hostIP.split("/");// 按‘/’分隔，取第一个
			hostIP = arr[0];
			indexList.get(i).setImage(indexList.get(i).getImage().replace(hostIP, ip));
		}
		Map map1 = new HashMap();
		map1.put("topic", indexList);
		map.put("data", map1);
		String param = JSON.toJSONString(map);
		return param;
	}

	@RequestMapping(value = "specialDesc", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String specialDesc(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("topicId"));
		List<IndexSpecial> indexList = appIndexService.getDesc(id);

		String param = JSON.toJSONString(indexList);
		return param;
	}

	// 首页
	@RequestMapping(value = "initHome", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String initHome(HttpServletRequest request, HttpServletResponse response) throws UnknownHostException {
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		String ip = InetAddress.getLocalHost().getHostAddress() + ":" + request.getLocalPort();
		List<IndexPic> slideshow = appIndexService.getlist();
		for (int i = 0; i < slideshow.size(); i++) {
			String hostIP = slideshow.get(i).getImage();
			hostIP = hostIP.replace("http://", "");// 去除http和https前缀
			String[] arr = hostIP.split("/");// 按‘/’分隔，取第一个
			hostIP = arr[0];
			slideshow.get(i).setImage(slideshow.get(i).getImage().replace(hostIP, ip));
		}
		List<AnalysisType> analysis = appIndexService.getIconList();
		for (int i = 0; i < analysis.size(); i++) {
			String hostIP = analysis.get(i).getIcon_url();
			hostIP = hostIP.replace("http://", "");// 去除http和https前缀
			String[] arr = hostIP.split("/");// 按‘/’分隔，取第一个
			hostIP = arr[0];
			analysis.get(i).setIcon_url(analysis.get(i).getIcon_url().replace(hostIP, ip));
		}
		List<IndexSpecial> topic = appIndexService.getIndexSpecialList();
		for (int i = 0; i < topic.size(); i++) {
			String hostIP = topic.get(i).getImage();
			hostIP = hostIP.replace("http://", "");// 去除http和https前缀
			String[] arr = hostIP.split("/");// 按‘/’分隔，取第一个
			hostIP = arr[0];
			topic.get(i).setImage(topic.get(i).getImage().replace(hostIP, ip));
		}
		Map map1 = new HashMap();
		map1.put("slideshow", slideshow);
		map1.put("analysis", analysis);
		map1.put("topic", topic);
		map.put("data", map1);
		String param = JSON.toJSONString(map);
		return param;
	}

}
