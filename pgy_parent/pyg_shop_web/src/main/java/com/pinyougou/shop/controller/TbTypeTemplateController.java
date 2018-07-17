package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TbTypeTemplateService;
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
@RequestMapping("/typeTemplate")
public class TbTypeTemplateController {

    @Reference
    private TbTypeTemplateService tbTypeTemplateService;


    /**
     * 查询所有品牌
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbTypeTemplate> findAll(){
        return tbTypeTemplateService.findAll();
    }

    @RequestMapping("/findSpecList/{id}")
    public List<Map> findSpecList(@PathVariable("id") Long id){
        return tbTypeTemplateService.findSpecList(id);
    }

    /**
     * 查询品牌分页的结果集
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/typeTemplatePage/{pageNum}/{pageSize}")
    public PageResult findTypeTemplatePage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return tbTypeTemplateService.findTypeTemplatePage(pageNum,pageSize);
    }

    /**
     * 添加单个品牌信息
     * @param tbTypeTemplate
     * @return
     */
    @RequestMapping("/typeTemplateSave")
    public Result saveTypeTemplate(@RequestBody TbTypeTemplate tbTypeTemplate){
        try {
            tbTypeTemplateService.saveTypeTemplate(tbTypeTemplate);
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
    @RequestMapping("/typeTemplateInitFind/{id}")
    public TbTypeTemplate typeTemplateInitFind(@PathVariable("id") Long id) {
        return tbTypeTemplateService.typeTemplateInitFind(id);
    }

    /**
     * 修改单个品牌
     * @param tbTypeTemplate
     * @return
     */
    @RequestMapping("/typeTemplateUpdate")
    public Result typeTemplateUpdate(@RequestBody TbTypeTemplate tbTypeTemplate) {
        try {
            tbTypeTemplateService.typeTemplateUpdate(tbTypeTemplate);
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
    @RequestMapping("/typeTemplateDel/{ids}")
    public Result typeTemplateDel(@PathVariable("ids") Long[] ids) {
        try {
            tbTypeTemplateService.typeTemplateDel(ids);
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
     * @param tbTypeTemplate
     * @return
     */
    @RequestMapping("/searchTypeTemplates/{pageNum}/{pageSize}")
    public PageResult searchTypeTemplates(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody TbTypeTemplate tbTypeTemplate) {
        return tbTypeTemplateService.searchTypeTemplate(pageNum,pageSize,tbTypeTemplate);
    }

}
