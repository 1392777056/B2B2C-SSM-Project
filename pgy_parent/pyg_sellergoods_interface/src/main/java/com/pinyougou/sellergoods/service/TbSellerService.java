package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeller;
import domaincommon.PageResult;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 18:54
 */
public interface TbSellerService {
    /**
     * 注册商家  0
     * @param tbSeller
     */
    void getRegiterSellerUser(TbSeller tbSeller);

    /**
     * 查找所有审核
     * @return
     */
    List<TbSeller> findAll();

    /**
     * 查询审核分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findSellerPage(int pageNum, int pageSize);

    /**
     * 添加单个审核信息
     * @param tbSeller
     */
    void saveSeller(TbSeller tbSeller);

    /**
     * 查询单个审核
     * @param id
     * @return
     */
     TbSeller sellerInitFind(Long id);

    /**
     * 修改单个审核
     * @param tbSeller
     */
    void sellerUpdate(TbSeller tbSeller);

    /**
     * 删除单个审核
     * @param ids
     */
    void sellerDel(Long[] ids);

    /**
     * 搜索审核功能
     * @param pageNum
     * @param pageSize
     * @param tbSeller
     * @return
     */
    PageResult searchSeller(int pageNum, int pageSize, TbSeller tbSeller);

}
