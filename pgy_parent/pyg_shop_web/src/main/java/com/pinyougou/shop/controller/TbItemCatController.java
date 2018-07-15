package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.TbItemCatService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:38
 */
@RestController
@RequestMapping("/itemCat")
public class TbItemCatController {

    @Reference
    private TbItemCatService tbItemCatService;

    @RequestMapping("/findParentId1/{parentId}")
    public List<TbItemCat> findParentId(@PathVariable("parentId") Long parentId) {
        return tbItemCatService.findParentId1(parentId);
    }
}
