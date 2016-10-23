package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.cms.entity.Article;

/**
 * @author linjm
 * @email linjianmao@gmail.com
 * @date 2016/10/14
 */
public interface SolrService {
	
	/**
	 * 获取分页列表
	 * @return
	 */
	Page doSearch(Page<Article> page, String q, String categoryId, String beginDate, String endDate) throws Exception;
	
	/**
	 * 创建索引
	 * @return
	 */
	int creatIndex(List<Article> list) throws Exception;
	
	/**
	 * 删除索引
	 * @return
	 */
	public int deleteIndexs(List<Article> list) throws Exception;
	
	/**
	 * 更新索引
	 * @return
	 */
	int updateIndex() throws Exception;

	

}
