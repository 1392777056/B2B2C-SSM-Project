package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbGoods;
import domainGroup.Goods;
import domaincommon.PageResult;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/15 9:34
 */
public interface TbGoodsService {

    /**
     * 添加商品
     * @param goods
     */
    void getAddGoods(Goods goods);

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbGoods
     * @return
     */
    PageResult searchGoods(int pageNum, int pageSize, TbGoods tbGoods);


    void updateAuditStatus(Long[] selectIds, String auditStatus);
    void updateMarketable(Long[] selectIds, String isMarketable);

    void deleIsDele(Long[] selectIds, String isDeleid);

    Goods findOne(Long goodsId);

    List<Goods> findAllGoods();
}
