package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import domainGroup.Specification;
import domaincommon.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 18:54
 */
public interface TbSpecificationService {


    /**
     * 查找所有规格
     * @return
     */
    List<TbSpecification> findAll();

    /**
     * 查询规格分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findSpecificationPage(int pageNum, int pageSize);

    /**
     * 添加单个规格信息
     * @param specification
     */
    void saveSpecification(Specification specification);

    /**
     * 查询单个规格
     * @param id
     * @return
     */
     Specification specificationInitFind(Long id);

    /**
     * 修改单个规格
     * @param specification
     */
    void specificationUpdate(Specification specification);

    /**
     * 删除单个规格
     * @param ids
     */
    void specificationDel(Long[] ids);

    /**
     * 搜索规格功能
     * @param pageNum
     * @param pageSize
     * @param tbSpecification
     * @return
     */
    PageResult searchSpecification(int pageNum, int pageSize, TbSpecification tbSpecification);

    List<Map> findSpecificationList();
}
