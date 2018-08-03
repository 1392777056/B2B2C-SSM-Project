package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.cart.service.TbCartService;
import com.pinyougou.utils.CookieUtil;
import domainGroup.Carts;
import domaincommon.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/30 10:35
 */
@RestController
@RequestMapping("/cart")
public class TbCartController {

    @Reference
    private TbCartService tbCartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    /**
     * sessionId存入Cookedid中
     * @return
     */
    public String getSessionId() {
        String sessionId = CookieUtil.getCookieValue(request, "cartCookie", "utf-8");
        if (sessionId == null) {
            sessionId = session.getId();
            CookieUtil.setCookie(request,response,"cartCookie",sessionId,48*60*60,"utf-8");
        }
        return sessionId;
    }

    /**
     * 查询购物车数据
     * @return
     */
    @RequestMapping("/findCartList")
    public List<Carts> findCartList(){
        String sessionId = getSessionId();
        List<Carts> cartsList_sessionId = tbCartService.findCartToListRedis(sessionId);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!"userName".equals("anonymousUser")) {
            List<Carts> cartsList_userName = tbCartService.findCartToListRedis(userName);
            // 合并，需要判断session是否为空，如果不为空的话就让未登录的数据和已登录的数据 购物车合并
            if (cartsList_sessionId.size() != 0) {
                // 进行合并
                cartsList_userName = tbCartService.mergeCartList(cartsList_sessionId,cartsList_userName);
                // 合并完成之后删除本地sessionId
                tbCartService.delCartItemSession(cartsList_sessionId);
                // 最后并保存到数据库中
                tbCartService.saveCartListToRedis(userName,cartsList_userName);
            }
            return cartsList_userName;
        }

        return cartsList_sessionId;
    }

    /**
     * 添加购物车数据
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addCartList/{itemId}/{num}")
    @CrossOrigin(origins = "http://item.pinyougou.com")
    public Result getCartToList(@PathVariable("itemId") Long itemId, @PathVariable("num") int num){
        String sessionId = getSessionId();
        try {
            // 1、在没登录的情况下，先用sessionId去查询 用户加入购物车商品的东西。
            List<Carts> cartsList = findCartList();
            // 2、这是新添加的商品并存放在一起
            cartsList = tbCartService.addCartToNewList(cartsList,itemId,num);
            // 3、根据sessionId，以及传过来的对象去保存到Redis中
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!"userName".equals("anonymousUser")) {
                tbCartService.saveCartListToRedisByUsername(userName,cartsList);
            } else {
                tbCartService.saveCartListToRedis(sessionId,cartsList);
            }
            return new Result(true,"保存成功");
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"保存失败");
        }

    }

}
