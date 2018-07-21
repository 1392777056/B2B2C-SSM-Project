package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/21 22:02
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map getSearchKeywordsInit(Map paramsMap) {

        SimpleHighlightQuery highlightQuery = new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_title").is(paramsMap.get("keywords"));
        highlightQuery.addCriteria(criteria);
        // 设置条数
        highlightQuery.setRows(20);
        HighlightPage<TbItem> tbItems = solrTemplate.queryForHighlightPage(highlightQuery, TbItem.class);
        // 查询的数据
        List<TbItem> itemList = tbItems.getContent();
        HashMap resultMap = new HashMap();
        resultMap.put("itemList",itemList);
        return resultMap;
    }
}
