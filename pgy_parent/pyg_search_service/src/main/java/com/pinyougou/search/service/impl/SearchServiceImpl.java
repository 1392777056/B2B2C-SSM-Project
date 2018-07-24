package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map getSearchKeywordsInit(Map paramsMap) {

        HashMap resultMap = new HashMap();

        // 1.使用简单的查询
        Query query = new SimpleQuery();
        // 2.根据输入的关键字去查询
        Criteria keyword = new Criteria("item_keywords").is(paramsMap.get("keywords"));
        query.addCriteria(keyword);

        // 3.根据分类域字段去分组
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        query.setGroupOptions(groupOptions);

        // 4.使用solr分组查询
        GroupPage<TbItem> groupPage = solrTemplate.queryForGroupPage(query, TbItem.class);
        // 5.得到这分类下面的结果集
        GroupResult<TbItem> groupResult = groupPage.getGroupResult("item_category");
        // 6.获取每个组下面的条目
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        // 7.获取每个组下的数据
        List<GroupEntry<TbItem>> content = groupEntries.getContent();
        // 8.循环遍历组中的数组，并且存放入集合中
        List<String> categoryList = new ArrayList<String>();
        for (GroupEntry<TbItem> tbItemGroupEntry : content) {
            categoryList.add(tbItemGroupEntry.getGroupValue());
        }
        // 9.将分类的数据，存放到大的Map中
        resultMap.put("categoryList",categoryList);

        //=======================  根据分类去查询下面    ==============================
        // 10.判断分类下是否有值，然后在进行分类 查询下面的品牌
        if (categoryList.size()>0){
           List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(categoryList.get(0));
           resultMap.put("brandList",brandList);
           List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(categoryList.get(0));
           resultMap.put("specList",specList);

        }

        //============================================
        /* 关键字查询 以及 高亮查询 */
        SimpleHighlightQuery highlightQuery = new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_keywords").is(paramsMap.get("keywords"));
        highlightQuery.addCriteria(criteria);
        // 设置高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<span style=\"color:red\">");
        highlightOptions.setSimplePostfix("</span>");
        highlightQuery.setHighlightOptions(highlightOptions);

        // 添加过滤条件 brand category
        SimpleFilterQuery filterQuery = new SimpleFilterQuery();
        if (paramsMap.get("category")!=null&&!paramsMap.get("category").equals("")){
            filterQuery.addCriteria(new Criteria("item_category").is(paramsMap.get("category")));
        }
        if (paramsMap.get("brand")!=null&&!paramsMap.get("brand").equals("")){
            filterQuery.addCriteria(new Criteria("item_brand").is(paramsMap.get("brand")));
        }
        Map<String,String> map = (Map) paramsMap.get("spec");
        for (String key : map.keySet()) {
            filterQuery.addCriteria(new Criteria("item_spec_"+key).is(map.get(key)));
        }

        if (paramsMap.get("price")!=null&&!paramsMap.get("price").equals("")){
            String[] prices = (paramsMap.get("price")+"").split("-");
            if (!"*".equals(prices[1])){
                filterQuery.addCriteria(new Criteria("item_price").between(prices[0],prices[1],true,true));
            } else {
                filterQuery.addCriteria(new Criteria("item_price").greaterThanEqual(prices[0]));
            }
        }

        /* 价格升序 */
        if (paramsMap.get("sort").equals("ASC")) {
            highlightQuery.addSort(new Sort(Sort.Direction.ASC,"item_price"));
        }
        /* 价格降序 */
        if (paramsMap.get("sort").equals("DESC")) {
            highlightQuery.addSort(new Sort(Sort.Direction.DESC,"item_price"));
        }

        /* 分页 */
        int page = Integer.parseInt(paramsMap.get("page")+"");

        // 设置条数
        highlightQuery.setOffset(page);
        highlightQuery.setRows(20);
        highlightQuery.addFilterQuery(filterQuery);
        HighlightPage<TbItem> tbItems = solrTemplate.queryForHighlightPage(highlightQuery, TbItem.class);

        /* 获取总页数 */
        resultMap.put("totalPage",tbItems.getTotalPages());

        // 查询的数据
        List<TbItem> itemList = tbItems.getContent();
        for (TbItem tbItem : itemList) {
            // 判断查到高亮有没有东西  ---- 复制域的应用，即使没有关键字，也能查出相对应的产品
            if (tbItems.getHighlights(tbItem) != null && tbItems.getHighlights(tbItem).size()>0) {
                HighlightEntry.Highlight highlight = tbItems.getHighlights(tbItem).get(0);
                List<String> snipplets = highlight.getSnipplets();
                if (snipplets!=null&&snipplets.size()>0){
                    tbItem.setTitle(snipplets.get(0));
                }
            }
        }


        resultMap.put("itemList",itemList);
        return resultMap;
    }
}
