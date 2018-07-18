package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.TbGoodsService;
import domainGroup.Goods;
import domaincommon.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Autowired
    private TbSellerMapper tbSellerMapper;

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

        if ("1".equals(tbGoods.getIsEnableSpec())) {

            List<TbItem> tbItems = goods.getItemList();
            for (TbItem tbItem : tbItems) {
                String title = tbGoods.getGoodsName();
                Map<String,String> map = JSON.parseObject(tbItem.getSpec(), Map.class);
                for (String key : map.keySet()) {
                    title += " "+key+map.get(key);
                }
                // 获取名称
                tbItem.setTitle(title);
                tbItem =createItem(tbGoods, tbGoodsDesc, tbItem);
                tbItemMapper.insert(tbItem);
            }
        } else {
            TbItem tbItem = new TbItem();
            tbItem.setTitle(tbGoods.getGoodsName());
            tbItem=createItem(tbGoods, tbGoodsDesc, tbItem);
            // 数量，可以成为库存
            tbItem.setNum(9999);
            // 商品价格
            tbItem.setPrice(tbGoods.getPrice());
            // 空的话，去初始化spec这个属性
            tbItem.setSpec("{}");
            // 默认1
            tbItem.setIsDefault("1");
            tbItemMapper.insert(tbItem);
        }



    }

    /**
     * 创建item输入的对象
     * @param tbGoods
     * @param tbGoodsDesc
     * @param tbItem
     * @return
     */
    private TbItem createItem(TbGoods tbGoods, TbGoodsDesc tbGoodsDesc, TbItem tbItem) {

        // 获取副标题
        tbItem.setSellPoint(tbGoods.getCaption());
        // 获取图片   默认是spu商品图片的第一个，前提是有图片
           /* [{"color":"白色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVtNlimALgj2AA7sGmpR00s538.jpg"}
            {"color":"黑色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVtNljOAL2OIAAydQrCFW_k078.jpg"}]*/
        List<Map> maps = JSON.parseArray(tbGoodsDesc.getItemImages(), Map.class);
        if (maps != null && maps.size()>0){
            tbItem.setImage(maps.get(0).get("url")+"");
        }
        // categoryId 第三级id
        tbItem.setCategoryid(tbGoods.getCategory3Id());
        // 创建时间
        tbItem.setCreateTime(new Date());
        // 更新时间
        tbItem.setUpdateTime(new Date());
        // 得到商品id
        tbItem.setGoodsId(tbGoods.getId());
        // 得到商家id
        tbItem.setSellerId(tbGoods.getSellerId());
        // 得到第三级分类名称
        tbItem.setCategory(tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());
        // 获取品牌的名称
        tbItem.setBrand(tbBrandMapper.selectByPrimaryKey(tbGoods.getBrandId()).getName());
        // 获取商家的名称
        tbItem.setSeller(tbSellerMapper.selectByPrimaryKey(tbGoods.getSellerId()).getNickName());

        return tbItem;
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
