package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.sellergoods.service.TbGoodsService;
import domainGroup.Goods;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/15 9:28
 */
@RestController
@RequestMapping("/goods")
public class TbGoodsController {

    @Reference
    private TbGoodsService tbGoodsService;

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result getAddGoods(@RequestBody Goods goods) {
        try {
            goods.getTbGoods().setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());
            tbGoodsService.getAddGoods(goods);
            return new Result(true,"添加商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加商品失败");
        }
    }

    @RequestMapping("/sreachGoods/{pageNum}/{pageSize}")
    public PageResult sreachGoods(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody TbGoods tbGoods) {
        return tbGoodsService.searchGoods(pageNum,pageSize,tbGoods);
    }

    @RequestMapping("/updateMarketable/{selectIds}/{isMarketable}")
    public Result updateMarketable(@PathVariable("selectIds") Long[] selectIds, @PathVariable("isMarketable") String isMarketable) {
        try {
            tbGoodsService.updateMarketable(selectIds,isMarketable);
            return new Result(true,"");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"状态修改失败");
        }
    }

    @RequestMapping("/deleIsDele/{selectIds}/{IsDeleid}")
    public Result deleIsDele(@PathVariable("selectIds") Long[] selectIds, @PathVariable("IsDeleid") String IsDeleid) {
        try {
            tbGoodsService.deleIsDele(selectIds,IsDeleid);
            return new Result(true,"");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"状态修改失败");
        }
    }

}
