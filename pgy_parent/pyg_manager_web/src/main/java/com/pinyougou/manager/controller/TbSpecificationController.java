package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.TbSpecificationService;
import domainGroup.Specification;
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
@RequestMapping("/specification")
public class TbSpecificationController {

    @Reference
    private TbSpecificationService tbSpecificationService;

    /**
     * 模板 ---  查询所有规格
     * @return
     */
    @RequestMapping("/findSpecificationList")
    public List<Map> findSpecificationList(){
        return tbSpecificationService.findSpecificationList();
    }
    
    /**
     * 查询所有规格
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSpecification> findAll(){
        return tbSpecificationService.findAll();
    }

    /**
     * 查询规格分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/specificationPage/{pageNum}/{pageSize}")
    public PageResult findSpecificationPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbSpecificationService.findSpecificationPage(pageNum,pageSize);
    }

    /**
     * 添加单个规格信息
     * @param specification
     * @return
     */
    @RequestMapping("/specificationSave")
    public Result saveSpecification(@RequestBody Specification specification){
        try {
            tbSpecificationService.saveSpecification(specification);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 查询单个规格
     * @param id
     * @return
     */
    @RequestMapping("/specificationInitFind/{id}")
    public Specification specificationInitFind(@PathVariable("id") Long id) {
        return tbSpecificationService.specificationInitFind(id);
    }

    /**
     * 修改单个规格
     * @param specification
     * @return
     */
    @RequestMapping("/specificationUpdate")
    public Result specificationUpdate(@RequestBody Specification specification) {
        try {
            tbSpecificationService.specificationUpdate(specification);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }

    /**
     * 删除单个规格
     * @param ids
     * @return
     */
    @RequestMapping("/specificationDel/{ids}")
    public Result specificationDel(@PathVariable("ids") Long[] ids) {
        try {
            tbSpecificationService.specificationDel(ids);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     * 搜索规格功能
     * @param pageNum
     * @param pageSize
     * @param tbSpecification
     * @return
     */
    @RequestMapping("/searchSpecifications/{pageNum}/{pageSize}")
    public PageResult searchSpecifications(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbSpecification tbSpecification) {
        return tbSpecificationService.searchSpecification(pageNum,pageSize,tbSpecification);
    }

}
