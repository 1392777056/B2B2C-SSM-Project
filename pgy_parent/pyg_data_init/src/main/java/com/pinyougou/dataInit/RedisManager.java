package com.pinyougou.dataInit;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/22 16:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class RedisManager {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void InitRedis() {

        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(null);
        for (TbItemCat tbItemCat : tbItemCats) {
            // 初始化分类与品牌的数据
            TbTypeTemplate tbTypeTemplate = tbTypeTemplateMapper.selectByPrimaryKey(tbItemCat.getTypeId());
            List<Map> brandList = JSON.parseArray(tbTypeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(tbItemCat.getName(),brandList);

            // 初始化分类与规格的数据
            String specIds = tbTypeTemplate.getSpecIds();
            List<Map> specList = JSON.parseArray(specIds, Map.class);
            for (Map map : specList) {
                TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
                tbSpecificationOptionExample.createCriteria().andSpecIdEqualTo(Long.parseLong(map.get("id")+""));
                List<TbSpecificationOption> specificationOptionList = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
                map.put("options",specificationOptionList);
            }
            redisTemplate.boundHashOps("specList").put(tbItemCat.getName(),specList);
        }
    }

}
