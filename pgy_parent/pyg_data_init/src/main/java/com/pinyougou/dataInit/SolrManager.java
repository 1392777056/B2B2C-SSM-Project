package com.pinyougou.dataInit;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/21 19:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class SolrManager {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * spring-data-solr演练 -- 添加
     */
    @Test
    public void getSolr(){
        TbItem tbItem = new TbItem();
        tbItem.setId(1l);
        tbItem.setTitle("苹果XXX");
        tbItem.setGoodsId(123431212l);
        tbItem.setPrice(new BigDecimal(199.11));
        tbItem.setImage("12331231");
        tbItem.setCategory("手机");
        tbItem.setSeller("德者");
        tbItem.setBrand("苹果");
        solrTemplate.saveBean(tbItem);
        solrTemplate.commit();
    }

    /**
     * spring-data-solr演练 -- 修改
     */
    @Test
    public void getUpdateSolr(){
        TbItem tbItem = new TbItem();
        tbItem.setId(1l);
        tbItem.setTitle("苹果8");
        tbItem.setGoodsId(12342l);
        tbItem.setPrice(new BigDecimal(8999.99));
        tbItem.setImage("888888");
        tbItem.setCategory("手机");
        tbItem.setSeller("德哲");
        tbItem.setBrand("苹果");
        solrTemplate.saveBean(tbItem);
        solrTemplate.commit();
    }

    /**
     * spring-data-solr演练 -- 删除
     */
    @Test
    public void getdeleSolr(){

        // 根据id去删除
        /*solrTemplate.deleteById("1");*/
        // 根据对象去删除所有
        /*SolrDataQuery solrDataQuery = new SimpleQuery("*:*");*/
        SimpleQuery simpleQuery = new SimpleQuery("item_goodsid:149187842867980");
        solrTemplate.delete(simpleQuery);
        /*solrTemplate.delete(solrDataQuery);*/
        solrTemplate.commit();
    }

    @Autowired
    private TbItemMapper tbItemMapper;
    /**
     * spring-data-solr演练 -- 查询
     */
    @Test
    public void getfindSolr(){

       List<TbItem> items = tbItemMapper.selectFindItems();
        for (TbItem item : items) {

            item.setSpecMap(JSON.parseObject(item.getSpec(),Map.class));
        }
       solrTemplate.saveBeans(items);
       solrTemplate.commit();
    }


}
