package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import domaincommon.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 18:54
 */
public interface TbBrandService {


    /**
     * 查找所有品牌
     * @return
     */
    List<TbBrand> findAll();

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findBrandPage(int pageNum, int pageSize);

    /**
     * 添加单个品牌信息
     * @param tbBrand
     */
    void saveBrand(TbBrand tbBrand);

    /**
     * 查询单个品牌
     * @param id
     * @return
     */
     TbBrand brandInitFind(Long id);

    /**
     * 修改单个品牌
     * @param tbBrand
     */
    void brandUpdate(TbBrand tbBrand);

    /**
     * 删除单个品牌
     * @param ids
     */
    void brandDel(Long[] ids);

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbBrand
     * @return
     */
    PageResult searchBrand(int pageNum, int pageSize, TbBrand tbBrand);

    /**
     * 模板 ----  查询所有品牌
     * @return
     */
    List<Map> findBrandList();
}
