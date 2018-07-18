package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.sellergoods.service.TbGoodsService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/18 8:29
 */
@RestController
@RequestMapping("/goodsController")
public class TbGoodsController {

    @Reference
    private TbGoodsService goodsService;

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbGoods
     * @return
     */
    @RequestMapping("/sreachGoods/{pageNum}/{pageSize}")
    public PageResult sreachGoods(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody TbGoods tbGoods) {
        return goodsService.searchGoods(pageNum,pageSize,tbGoods);
    }

    @RequestMapping("/updateAuditStatus/{selectIds}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable("selectIds") Long[] selectIds, @PathVariable("auditStatus") String auditStatus) {
        try {
            goodsService.updateAuditStatus(selectIds,auditStatus);
            return new Result(true,"");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"状态修改失败");
        }
    }
}
