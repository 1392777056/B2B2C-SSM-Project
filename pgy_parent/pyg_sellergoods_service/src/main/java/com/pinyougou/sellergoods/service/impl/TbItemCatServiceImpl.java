package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.sellergoods.service.TbItemCatService;
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
public class TbItemCatServiceImpl implements TbItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 查询所有分类
     * @return
     */
    public List<TbItemCat> findAll() {
        return tbItemCatMapper.selectByExample(null);
    }

    /**
     * 查询分类分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findItemCatPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page page = (Page) tbItemCatMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加单个分类信息
     * @param tbItemCat
     */
    public void saveItemCat(TbItemCat tbItemCat) {
            tbItemCatMapper.insert(tbItemCat);
    }

    /**
     * 查询单个分类
     * @param id
     * @return
     */
    public TbItemCat itemCatInitFind(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }

    /**
     *修改单个分类
     * @param tbItemCat
     */
    public void itemCatUpdate(TbItemCat tbItemCat) {
        tbItemCatMapper.updateByPrimaryKey(tbItemCat);
    }

    /**
     * 删除单个分类
     * @param ids
     */
    public void itemCatDel(Long[] ids) {
        for (Long id : ids) {
            TbItemCatExample tbItemCatExample = new TbItemCatExample();
            tbItemCatExample.createCriteria().andParentIdEqualTo(id);
            List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
            if (tbItemCats == null||tbItemCats.size()==0) {
                tbItemCatMapper.deleteByPrimaryKey(id);
            }
        }

    }

    /**
     * 搜索分类功能
     * @param pageNum
     * @param pageSize
     * @param tbItemCat
     * @return
     */
    public PageResult searchItemCat(int pageNum, int pageSize, TbItemCat tbItemCat) {
        PageHelper.startPage(pageNum,pageSize);
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        if (StringUtils.isNotEmpty(tbItemCat.getName())){
            criteria.andNameLike("%"+tbItemCat.getName()+"%");
        }
       /* if (StringUtils.isNotEmpty(tbItemCat.getFirstChar())){
            criteria.andFirstCharEqualTo(tbItemCat.getFirstChar());
        }*/
        Page page = (Page) tbItemCatMapper.selectByExample(tbItemCatExample);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据父类id去查询
     * @param parentId
     * @return
     */
    public PageResult findParentId(int pageNum, int pageSize,Long parentId) {
        PageHelper.startPage(pageNum,pageSize);
        TbItemCatExample itemCatExample = new TbItemCatExample();
        itemCatExample.createCriteria().andParentIdEqualTo(parentId);
        Page page = (Page) tbItemCatMapper.selectByExample(itemCatExample);
        return new PageResult(page.getTotal(),page.getResult());
    }
    /*public List<TbItemCat> findParentId(Long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        itemCatExample.createCriteria().andParentIdEqualTo(parentId);
        return tbItemCatMapper.selectByExample(itemCatExample);
    }*/


}
