package com.pinyougou.manager.controller;

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
import java.util.Map;

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

    /**
     * 查询所有分类
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbItemCat> findAll(){
        return tbItemCatService.findAll();
    }

    /**
     * 查询分类分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/itemCatPage/{pageNum}/{pageSize}")
    public PageResult findItemCatPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbItemCatService.findItemCatPage(pageNum,pageSize);
    }

    /**
     * 添加单个分类信息
     * @param tbItemCat
     * @return
     */
    @RequestMapping("/itemCatSave")
    public Result saveItemCat(@RequestBody TbItemCat tbItemCat){
        try {
            tbItemCatService.saveItemCat(tbItemCat);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 查询单个分类
     * @param id
     * @return
     */
    @RequestMapping("/itemCatInitFind/{id}")
    public TbItemCat itemCatInitFind(@PathVariable("id") Long id) {
        return tbItemCatService.itemCatInitFind(id);
    }

    /**
     * 修改单个分类
     * @param tbItemCat
     * @return
     */
    @RequestMapping("/itemCatUpdate")
    public Result itemCatUpdate(@RequestBody TbItemCat tbItemCat) {
        try {
            tbItemCatService.itemCatUpdate(tbItemCat);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    /**
     * 删除单个分类
     * @param ids
     * @return
     */
    @RequestMapping("/itemCatDel/{ids}")
    public Result itemCatDel(@PathVariable("ids") Long[] ids) {
        try {
            tbItemCatService.itemCatDel(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"下面还有子集分类，请先把这个父类下全部子集分类删除完！");
        }
    }

    /**
     * 搜索分类功能
     * @param pageNum
     * @param pageSize
     * @param tbItemCat
     * @return
     */
    @RequestMapping("/searchItemCats/{pageNum}/{pageSize}")
    public PageResult searchItemCats(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbItemCat tbItemCat) {
        return tbItemCatService.searchItemCat(pageNum,pageSize,tbItemCat);
    }


    @RequestMapping("/findParentId/{pageNum}/{pageSize}/{parentId}")
    public PageResult findParentId(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@PathVariable("parentId") Long parentId) {
        return tbItemCatService.findParentId(pageNum,pageSize,parentId);
    }

    /*@RequestMapping("/findParentId/{parentId}")
    public List<TbItemCat> findParentId(@PathVariable("parentId") Long parentId) {
        return tbItemCatService.findParentId(parentId);
    }*/
}
