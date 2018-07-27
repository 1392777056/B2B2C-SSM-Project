package com.pinyougou.search.consumer;

import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/26 11:59
 */
public class ComsumerDeleSorlSynchronize implements MessageListener {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String goodsId = textMessage.getText();
            TbItemExample tbItemExample = new TbItemExample();
            tbItemExample.createCriteria().andGoodsIdEqualTo(Long.parseLong(goodsId));
            List<TbItem> items = tbItemMapper.selectByExample(tbItemExample);

            SimpleQuery simpleQuery = new SimpleQuery("item_goodsid:"+goodsId);
            solrTemplate.delete(simpleQuery);
            solrTemplate.commit();
            System.out.println("xiassss");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
