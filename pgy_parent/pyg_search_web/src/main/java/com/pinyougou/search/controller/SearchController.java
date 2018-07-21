package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/21 21:57
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("/searchKeywordsInit")
    public Map getSearchKeywordsInit(@RequestBody Map paramsMap){
        return searchService.getSearchKeywordsInit(paramsMap);
    }

}
