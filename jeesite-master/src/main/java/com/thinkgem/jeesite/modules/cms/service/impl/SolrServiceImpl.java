package com.thinkgem.jeesite.modules.cms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.SolrService;
import com.thinkgem.jeesite.modules.cms.utils.SolrUtil;


/**
 * @author linjm
 * @email linjianmao@gmail.com
 * @date 2016/10/14
 */
@Service
public class SolrServiceImpl implements SolrService {

//	@Autowired
//	private ArticleDao articleDao;

	@Override
	public int updateIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page doSearch(Page<Article> page, String q, String categoryId, String beginDate, String endDate) throws Exception{
		SolrClient solr = SolrUtil.getSolrClient(SolrUtil.CORE_ARTICLE);
		SolrQuery query = new SolrQuery();
		//高亮设置
		query.setHighlight(true);
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		query.addHighlightField("title");
		if(StringUtils.isNotEmpty(q)){
			query.set("q", q);
		}
		query.setStart((page.getPageNo()-1)*page.getPageSize());
		query.setRows(page.getPageSize());
		QueryResponse response = solr.query(query);
        SolrDocumentList solrDocuments= response.getResults();
        DocumentObjectBinder binder = solr.getBinder();
        List<Article> list = binder.getBeans(Article.class,solrDocuments);
		
		page.setCount(solrDocuments.getNumFound());
		page.setList(list);
		return page;
	}

	@Override
	public int creatIndex(List<Article> list) throws Exception {
//		Article a = new Article();
//		List<Article> list = articleDao.findAllList(a);
		System.out.println("文章总条数："+list.size());
		SolrClient solr= SolrUtil.getSolrClient(SolrUtil.CORE_ARTICLE);
        try {
			solr.addBeans(list);
			solr.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteIndexs(List list) throws Exception {
		SolrClient solr= SolrUtil.getSolrClient(SolrUtil.CORE_ARTICLE);
		List<String> ids = new ArrayList<String>();
		for(int i=0; i<list.size();i++){
			Article a = (Article)list.get(i);
			ids.add(a.getId());
		}
        try {
			solr.deleteById(ids);
			solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}



}
