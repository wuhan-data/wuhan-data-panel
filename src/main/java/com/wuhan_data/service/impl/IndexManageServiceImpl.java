package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.IndexManageMapper;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.service.IndexManageService;

@Service
public class IndexManageServiceImpl implements IndexManageService{
	
	@Autowired
	IndexManageMapper indexManageMapper;

	@Override
	public int add(IndexManage indexManage) {
		indexManageMapper.add(indexManage);
		return 0;
	}

	@Override
	public void delete(int id) {
		indexManageMapper.delete(id);
		
	}

	@Override
	public IndexManage get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(IndexManage indexManage) {
		
		indexManageMapper.update(indexManage);
	}

	@Override
	public List<IndexManage> list() {
		// TODO Auto-generated method stub
		return indexManageMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	  @Override
	    public List<IndexManage> list(Page page) {
	        // TODO Auto-generated method stub
	        return indexManageMapper.list(page);
	    }
	 
	    @Override
	    public int total() {
	        return indexManageMapper.total();
	    }
	    
	    //设置指标允许展示
		@Override
		public void per_show(int id) {
			
			indexManageMapper.per_show(id);
			
		}
		
		//设置指标不允许展示
		@Override
		public void no_per_show(int id) {
			indexManageMapper.no_per_show(id);
			
		}

		@Override
		public List<String> IndiSearch(String keyword) {
			List<String> indilist=indexManageMapper.IndiSearch(keyword);
			return indilist;
		}

		@Override
		public List<IndexManage> indiSearch(Map map) {
			// TODO Auto-generated method stub
		
			return indexManageMapper.indiSearch(map);
		}

		@Override
		public List<IndexManage> listAddPage(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return indexManageMapper.listAddPage(map);
		}

		@Override
		public int searchCount(Map<String, Object> mapSearch) {
			// TODO Auto-generated method stub
			return indexManageMapper.searchCount(mapSearch);
		}

		@Override
		public List<IndexManage> list1() {
			// TODO Auto-generated method stub
			return indexManageMapper.list1();
		}

		@Override
		public List<indi_TF> listTF() {
			// TODO Auto-generated method stub
			return indexManageMapper.listTF();
		}

		@Override
		public void add_init(IndexManage indexManage) {
			
			indexManageMapper.add_init(indexManage);
			
		}

		@Override
		public List<IndexManage> listIndi() {
			// TODO Auto-generated method stub
			return indexManageMapper.listIndi();
		}

		

}
