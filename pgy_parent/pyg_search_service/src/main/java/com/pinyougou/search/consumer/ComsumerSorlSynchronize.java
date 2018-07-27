package com.pinyougou.search.consumer;

import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

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
public class ComsumerSorlSynchronize implements MessageListener {

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
            solrTemplate.saveBeans(items);
            solrTemplate.commit();
            System.out.println("nice");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
