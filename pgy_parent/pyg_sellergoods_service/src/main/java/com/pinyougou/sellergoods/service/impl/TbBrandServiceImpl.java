package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.TbBrandService;
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
public class TbBrandServiceImpl implements TbBrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    /**
     * 查询所有品牌
     * @return
     */
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findBrandPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page page = (Page) tbBrandMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加单个品牌信息
     * @param tbBrand
     */
    public void saveBrand(TbBrand tbBrand) {
        if( tbBrand.getName() != null && tbBrand.getName() != "" && tbBrand.getFirstChar() != null && tbBrand.getFirstChar() != ""){
            tbBrandMapper.insert(tbBrand);
        }
    }

    /**
     * 查询单个品牌
     * @param id
     * @return
     */
    public TbBrand brandInitFind(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    /**
     *修改单个品牌
     * @param tbBrand
     */
    public void brandUpdate(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    /**
     * 删除单个品牌
     * @param ids
     */
    public void brandDel(Long[] ids) {
        for (Long id : ids) {
            tbBrandMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbBrand
     * @return
     */
    public PageResult searchBrand(int pageNum, int pageSize, TbBrand tbBrand) {
        PageHelper.startPage(pageNum,pageSize);
        TbBrandExample tbBrandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        if (StringUtils.isNotEmpty(tbBrand.getName())){
            criteria.andNameLike("%"+tbBrand.getName()+"%");
        }
        if (StringUtils.isNotEmpty(tbBrand.getFirstChar())){
            criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
        }
        Page page = (Page) tbBrandMapper.selectByExample(tbBrandExample);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 模板 ----  查询所有品牌
     * @return
     */
    public List<Map> findBrandList() {
        return tbBrandMapper.findBrandList();
    }


}
