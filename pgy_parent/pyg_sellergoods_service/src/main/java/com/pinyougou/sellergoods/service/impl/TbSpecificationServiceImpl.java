package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.sellergoods.service.TbSpecificationService;
import domainGroup.Specification;
import domaincommon.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:14
 */
@Service
@Transactional
public class TbSpecificationServiceImpl implements TbSpecificationService {

    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;

    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    /**
     * 查询所有规格
     * @return
     */
    public List<TbSpecification> findAll() {
        return tbSpecificationMapper.selectByExample(null);
    }

    /**
     * 查询规格分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findSpecificationPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page page = (Page) tbSpecificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加单个规格信息
     * @param specification
     */
    public void saveSpecification(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        tbSpecificationMapper.insert(tbSpecification);

        List<TbSpecificationOption> tbSpecificationOptions = specification.getTbSpecificationOptions();
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            tbSpecificationOptionMapper.insert(tbSpecificationOption);
        }

    }

    /**
     * 查询单个规格
     * @param id
     * @return
     */
    public Specification specificationInitFind(Long id) {
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        tbSpecificationOptionExample.createCriteria().andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptionList = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);
        specification.setTbSpecificationOptions(tbSpecificationOptionList);
        return  specification;

    }

    /**
     *修改单个规格
     * @param specification
     */
    public void specificationUpdate(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        tbSpecificationMapper.updateByPrimaryKey(tbSpecification);

        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        tbSpecificationOptionExample.createCriteria().andSpecIdEqualTo(tbSpecification.getId());
        tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);

        List<TbSpecificationOption> tbSpecificationOptions = specification.getTbSpecificationOptions();
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            tbSpecificationOptionMapper.insert(tbSpecificationOption);
        }

    }

    /**
     * 删除单个规格
     * @param ids
     */
    public void specificationDel(Long[] ids) {
        for (Long id : ids) {
            tbSpecificationMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 搜索规格功能
     * @param pageNum
     * @param pageSize
     * @param tbSpecification
     * @return
     */
    public PageResult searchSpecification(int pageNum, int pageSize, TbSpecification tbSpecification) {
        PageHelper.startPage(pageNum,pageSize);
        TbSpecificationExample tbSpecificationExample = new TbSpecificationExample();

        if (StringUtils.isNotEmpty(tbSpecification.getSpecName())){
            tbSpecificationExample.createCriteria().andSpecNameLike("%"+tbSpecification.getSpecName()+"%");
        }
        Page page = (Page) tbSpecificationMapper.selectByExample(tbSpecificationExample);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Map> findSpecificationList() {
        return tbSpecificationMapper.findSpecificationList();
    }


}
