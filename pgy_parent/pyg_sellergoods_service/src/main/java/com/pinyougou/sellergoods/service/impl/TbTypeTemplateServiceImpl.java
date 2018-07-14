package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.sellergoods.service.TbTypeTemplateService;
import domaincommon.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:14
 */
@Service
@Transactional
public class TbTypeTemplateServiceImpl implements TbTypeTemplateService {

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    /**
     * 查询所有品牌
     * @return
     */
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateMapper.selectByExample(null);
    }

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findTypeTemplatePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page page = (Page) tbTypeTemplateMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加单个品牌信息
     * @param tbTypeTemplate
     */
    public void saveTypeTemplate(TbTypeTemplate tbTypeTemplate) {
            tbTypeTemplateMapper.insert(tbTypeTemplate);
    }

    /**
     * 查询单个品牌
     * @param id
     * @return
     */
    public TbTypeTemplate typeTemplateInitFind(Long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     *修改单个品牌
     * @param tbTypeTemplate
     */
    public void typeTemplateUpdate(TbTypeTemplate tbTypeTemplate) {
        tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
    }

    /**
     * 删除单个品牌
     * @param ids
     */
    public void typeTemplateDel(Long[] ids) {
        for (Long id : ids) {
            tbTypeTemplateMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbTypeTemplate
     * @return
     */
    public PageResult searchTypeTemplate(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate) {
        PageHelper.startPage(pageNum,pageSize);
        TbTypeTemplateExample tbTypeTemplateExample = new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = tbTypeTemplateExample.createCriteria();
        if (StringUtils.isNotEmpty(tbTypeTemplate.getName())){
            criteria.andNameLike("%"+tbTypeTemplate.getName()+"%");
        }
        
        Page page = (Page) tbTypeTemplateMapper.selectByExample(tbTypeTemplateExample);
        return new PageResult(page.getTotal(),page.getResult());
    }


}
