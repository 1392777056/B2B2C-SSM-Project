package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:38
 */
@RestController
@RequestMapping("/seller")
public class TbSellerController {

    @Reference
    private TbSellerService tbSellerService;

    /**
     * 查询所有审核
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSeller> findAll(){
        return tbSellerService.findAll();
    }

    /**
     * 查询审核分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/sellerPage/{pageNum}/{pageSize}")
    public PageResult findSellerPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbSellerService.findSellerPage(pageNum,pageSize);
    }

    /**
     * 添加单个审核信息
     * @param tbSeller
     * @return
     */
    @RequestMapping("/sellerSave")
    public Result saveSeller(@RequestBody TbSeller tbSeller){
        try {
            tbSellerService.saveSeller(tbSeller);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 查询单个审核
     * @param id
     * @return
     */
    @RequestMapping("/sellerInitFind/{id}")
    public TbSeller sellerInitFind(@PathVariable("id") Long id) {
        return tbSellerService.sellerInitFind(id);
    }

    /**
     * 修改单个审核
     * @param tbSeller
     * @return
     */
    @RequestMapping("/sellerUpdate")
    public Result sellerUpdate(@RequestBody TbSeller tbSeller) {
        try {
            tbSellerService.sellerUpdate(tbSeller);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    /**
     * 删除单个审核
     * @param ids
     * @return
     */
    @RequestMapping("/sellerDel/{ids}")
    public Result sellerDel(@PathVariable("ids") Long[] ids) {
        try {
            tbSellerService.sellerDel(ids);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     * 搜索审核功能
     * @param pageNum
     * @param pageSize
     * @param tbSeller
     * @return
     */
    @RequestMapping("/searchSellers/{pageNum}/{pageSize}")
    public PageResult searchSellers(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbSeller tbSeller) {
        return tbSellerService.searchSeller(pageNum,pageSize,tbSeller);
    }

}
