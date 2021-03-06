package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.sellergoods.service.TbSellerService;
import domaincommon.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
public class TbSellerServiceImpl implements TbSellerService {

    @Autowired
    private TbSellerMapper tbSellerMapper;

    /**
     * 注册商家  0
     * @param tbSeller
     */
    public void getRegiterSellerUser(TbSeller tbSeller) {
        tbSeller.setStatus("0");
        tbSellerMapper.insert(tbSeller);
    }

    /**
     * 查询所有审核
     * @return
     */
    public List<TbSeller> findAll() {
        return tbSellerMapper.selectByExample(null);
    }

    /**
     * 查询审核分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findSellerPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page page = (Page) tbSellerMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加单个审核信息
     * @param tbSeller
     */
    public void saveSeller(TbSeller tbSeller) {
        /*if( tbSeller.getName() != null && tbSeller.getName() != "" && tbSeller.getFirstChar() != null && tbSeller.getFirstChar() != ""){*/
            tbSellerMapper.insert(tbSeller);
        /*}*/
    }

    /**
     * 查询单个审核
     */
    public TbSeller sellerInitFind(String id) {
        return tbSellerMapper.selectByPrimaryKey(id);
    }

    /**
     *修改单个审核
     */
    public void sellerUpdate(String status,String sellerId) {
        Map<String, String> map = new HashMap<>();
        map.put("status",status);
        map.put("sellerId",sellerId);
        tbSellerMapper.updateSellerStatus(map);
    }


    /**
     * 搜索审核功能
     * @param pageNum
     * @param pageSize
     * @param tbSeller
     * @return
     */
    public PageResult searchSeller(int pageNum, int pageSize, TbSeller tbSeller) {
        PageHelper.startPage(pageNum,pageSize);
        TbSellerExample tbSellerExample = new TbSellerExample();
        TbSellerExample.Criteria criteria = tbSellerExample.createCriteria();
        if (StringUtils.isNotEmpty(tbSeller.getName())){
            criteria.andNameLike("%"+tbSeller.getName() +"%");
        }
        if (StringUtils.isNotEmpty(tbSeller.getNickName())) {
            criteria.andNickNameEqualTo("%"+tbSeller.getNickName()+"%");
        }
        if (tbSeller.getStatus() != null && tbSeller.getStatus().length()>0) {
            criteria.andStatusEqualTo(tbSeller.getStatus());
        }
        Page page = (Page) tbSellerMapper.selectByExample(tbSellerExample);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TbSeller findBySellerId(String username) {
        TbSellerExample tbSellerExample = new TbSellerExample();
        tbSellerExample.createCriteria().andSellerIdEqualTo(username);
        List<TbSeller> tbSellers = tbSellerMapper.selectByExample(tbSellerExample);
        if (tbSellers != null && tbSellers.size() >0 ) {
            return tbSellers.get(0);
        }
        return null;
    }

    @Override
    public void updateSellerInfo(TbSeller tbSeller) {
        tbSellerMapper.updateByPrimaryKey(tbSeller);
    }


}
