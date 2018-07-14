package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.TbBrandService;
import domaincommon.PageResult;
import domaincommon.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/7 19:38
 */
@RestController
@RequestMapping("/brand")
public class TbBrandController {

    @Reference
    private TbBrandService tbBrandService;

    /**
     * 模板 ----  查询所有品牌
     * @return
     */
    @RequestMapping("/findBrandList")
    public List<Map> findBrandList(){
        return tbBrandService.findBrandList();
    }

    /**
     * 查询所有品牌
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return tbBrandService.findAll();
    }

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/brandPage/{pageNum}/{pageSize}")
    public PageResult findBrandPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbBrandService.findBrandPage(pageNum,pageSize);
    }

    /**
     * 添加单个品牌信息
     * @param tbBrand
     * @return
     */
    @RequestMapping("/brandSave")
    public Result saveBrand(@RequestBody TbBrand tbBrand){
        try {
            tbBrandService.saveBrand(tbBrand);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 查询单个品牌
     * @param id
     * @return
     */
    @RequestMapping("/brandInitFind/{id}")
    public TbBrand brandInitFind(@PathVariable("id") Long id) {
        return tbBrandService.brandInitFind(id);
    }

    /**
     * 修改单个品牌
     * @param tbBrand
     * @return
     */
    @RequestMapping("/brandUpdate")
    public Result brandUpdate(@RequestBody TbBrand tbBrand) {
        try {
            tbBrandService.brandUpdate(tbBrand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    /**
     * 删除单个品牌
     * @param ids
     * @return
     */
    @RequestMapping("/brandDel/{ids}")
    public Result brandDel(@PathVariable("ids") Long[] ids) {
        try {
            tbBrandService.brandDel(ids);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     * 搜索品牌功能
     * @param pageNum
     * @param pageSize
     * @param tbBrand
     * @return
     */
    @RequestMapping("/searchBrands/{pageNum}/{pageSize}")
    public PageResult searchBrands(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbBrand tbBrand) {
        return tbBrandService.searchBrand(pageNum,pageSize,tbBrand);
    }

}
