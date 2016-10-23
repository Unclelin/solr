package com.thinkgem.jeesite.modules.cms.utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * @author linjm
 * @email linjianmao@gmail.com
 * @date 2016/10/14
 */
public class SolrUtil {
	
    /**
     *  solr 服务器地址 非集群时指定
     */
    public static final String SOLR_SERVER = "http://127.0.0.1:8983/solr";
    
    /**
     *文章索引对应的 core 或collection
     *需要在solrf服务中创建对应的core
     */
    public static final String CORE_ARTICLE = "linjianmao_article_core";


    public  static SolrClient getSolrClient(String coreName){
        String urlString = SOLR_SERVER + "/" + coreName;
        return new HttpSolrClient(urlString);
    }
    
}
