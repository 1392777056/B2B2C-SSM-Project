package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItemCat;
import domaincommon.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 18:54
 */
public interface TbItemCatService {


    /**
     * 查找所有分类
     * @return
     */
    List<TbItemCat> findAll();

    /**
     * 查询分类分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findItemCatPage(int pageNum, int pageSize);

    /**
     * 添加单个分类信息
     * @param tbItemCat
     */
    void saveItemCat(TbItemCat tbItemCat);

    /**
     * 查询单个分类
     * @param id
     * @return
     */
     TbItemCat itemCatInitFind(Long id);

    /**
     * 修改单个分类
     * @param tbItemCat
     */
    void itemCatUpdate(TbItemCat tbItemCat);

    /**
     * 删除单个分类
     * @param ids
     */
    void itemCatDel(Long[] ids);

    /**
     * 搜索分类功能
     * @param pageNum
     * @param pageSize
     * @param tbItemCat
     * @return
     */
    PageResult searchItemCat(int pageNum, int pageSize, TbItemCat tbItemCat);

    /**
     * 根据父类id去查询
     * @param parentId
     * @return
     */
    PageResult findParentId(int pageNum, int pageSize,Long parentId);

    List<TbItemCat> findParentId1(Long parentId);
}
