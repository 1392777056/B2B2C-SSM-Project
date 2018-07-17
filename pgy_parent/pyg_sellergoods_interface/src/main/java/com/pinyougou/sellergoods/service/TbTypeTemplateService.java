package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import domaincommon.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 18:54
 */
public interface TbTypeTemplateService {


    /**
     * 查找所有品牌
     * @return
     */
    List<TbTypeTemplate> findAll();

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findTypeTemplatePage(int pageNum, int pageSize);

    /**
     * 添加单个品牌信息
     * @param tbTypeTemplate
     */
    void saveTypeTemplate(TbTypeTemplate tbTypeTemplate);

    /**
     * 查询单个品牌
     * @param id
     * @return
     */
     TbTypeTemplate typeTemplateInitFind(Long id);

    /**
     * 修改单个品牌
     * @param tbTypeTemplate
     */
    void typeTemplateUpdate(TbTypeTemplate tbTypeTemplate);

    /**
     * 删除单个品牌
     * @param ids
     */
    void typeTemplateDel(Long[] ids);

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbTypeTemplate
     * @return
     */
    PageResult searchTypeTemplate(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate);

    List<Map> findSpecList(Long id);
}
