package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.TbGoodsService;
import domainGroup.Goods;
import domaincommon.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/15 9:35
 */
@Service
public class TbGoodsServiceImpl implements TbGoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    /**
     * 添加商品
     * @param goods
     */
    public void getAddGoods(Goods goods) {
        TbGoods tbGoods = goods.getTbGoods();
        tbGoods.setAuditStatus("0");
        tbGoodsMapper.insert(tbGoods);
        TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
        tbGoodsDesc.setGoodsId(tbGoods.getId());
        tbGoodsDescMapper.insert(tbGoodsDesc);
    }


    public PageResult searchGoods(int pageNum, int pageSize, TbGoods tbGoods) {
        PageHelper.startPage(pageNum,pageSize);
        TbGoodsExample tbGoodsExample = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = tbGoodsExample.createCriteria();
        if (StringUtils.isNotEmpty(tbGoods.getGoodsName())){
            criteria.andGoodsNameLike("%"+tbGoods.getGoodsName()+"%");
        }
        Page page = (Page) tbGoodsMapper.selectByExample(tbGoodsExample);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
