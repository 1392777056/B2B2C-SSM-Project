package com.pinyougou.freemarker.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.sellergoods.service.TbGoodsService;
import domainGroup.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/24 20:36
 */
@RestController
@RequestMapping("/item")
public class ItemFreemarkerController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Reference
    private TbGoodsService tbGoodsService;

    @RequestMapping("/generatorToHtml/{goodsId}")
    public String generatorTohtml(@PathVariable("goodsId") Long goodsId){
        try {
            /* 1. 创建一个 Configuration 对象 */
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 2. 设置模板文件所在的路径。
            // 3. 设置模板文件使用的字符集。一般就是 utf-8.
            /* 4. 创建模板对象 */
            Template template = configuration.getTemplate("item.ftl");
            /* 5. 准备数据集 */
            Goods goods = tbGoodsService.findOne(goodsId);

            List<TbItem> itemList = goods.getItemList();
            for (TbItem tbItem : itemList) {
                Map map = new HashMap();
                /* 6. 创建fileWriter对象，并且制定生产文件的路径以及名称*/
                FileWriter fileWriter = new FileWriter(new File("D:\\itheima\\html\\"+tbItem.getId()+".html"));
                /* 7. 调用模板对象的 process 方法输出文件*/
                map.put("goods",goods);
                map.put("item",tbItem);
                template.process(map,fileWriter);
                /* 8. 关闭流 */
                fileWriter.close();
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "flail";
        }

    }

    @RequestMapping("/generatorToHtmlAll")
    public String generatorTohtml(){
        try {
            /* 1. 创建一个 Configuration 对象 */
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 2. 设置模板文件所在的路径。
            // 3. 设置模板文件使用的字符集。一般就是 utf-8.
            /* 4. 创建模板对象 */
            Template template = configuration.getTemplate("item.ftl");
            /* 5. 准备数据集 */
            List<Goods> allGoods = tbGoodsService.findAllGoods();
            for (Goods goods : allGoods) {
                List<TbItem> itemList = goods.getItemList();
                for (TbItem tbItem : itemList) {
                    Map map = new HashMap();
                    /* 6. 创建fileWriter对象，并且制定生产文件的路径以及名称*/
                    FileWriter fileWriter = new FileWriter(new File("D:\\itheima\\html\\"+tbItem.getId()+".html"));
                    /* 7. 调用模板对象的 process 方法输出文件*/
                    map.put("goods",goods);
                    map.put("item",tbItem);
                    template.process(map,fileWriter);
                    /* 8. 关闭流 */
                    fileWriter.close();
                }
            }

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "flail";
        }

    }


}
