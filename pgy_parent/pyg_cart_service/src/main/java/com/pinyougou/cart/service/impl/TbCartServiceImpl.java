package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.TbCartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import domainGroup.Carts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/30 10:38
 */
@Service
public class TbCartServiceImpl implements TbCartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public List<Carts> findCartToListRedis(String sessionId) {
        String cartStr = (String) redisTemplate.boundValueOps(sessionId).get();
        // 可能第一次取到的值是空值
        if (cartStr == null) {
            cartStr = "[]";
        }
        // 将carts对象转化成数组，让它至少不为NULL 至少得来的数据等于0 size
        List<Carts> cartsList = JSON.parseArray(cartStr, Carts.class);
        return cartsList;
    }

    /**
     * 实现加入购物车（逻辑重要）
     * @param cartsList
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public List<Carts> addCartToNewList(List<Carts> cartsList, Long itemId, int num) {
        // 1. 根据itemId去查询TbItem表中的数据
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        // 2. 判断这个TbItem所属的商家，购物车是否有数据
        if (tbItem != null){
            // 2.1.0 判断购物车是否此商家
            String sellerId = tbItem.getSellerId();
            Carts carts = findCartToSellerShopping(sellerId, cartsList);
            // 2.1.1 如果购物车列表有此商家.  否则,反之
            if (carts != null) {
                // 2.1.2 判断即将添加的商品是否属于此商家的购物车中，如果数据存在就让它累加
                List<TbOrderItem> tbOrderItemList = carts.getTbOrderItemList();
                TbOrderItem orderItem = findCartToOrderItem(itemId, tbOrderItemList);
                // 2.1.3 说明即将添加的商品在购物车中有，就让它去累加。否则，反之
                if (orderItem != null) {
                    orderItem.setNum(orderItem.getNum() + num);
                    // 如果输入的是小于 1
                    if (orderItem.getNum() < 1) {
                        tbOrderItemList.remove(orderItem);
                        if (tbOrderItemList.size() == 0) {
                            // 如果这件商品也不存在任何一个商家的话，整体移除
                            cartsList.remove(carts);
                        }
                    } else {
                        double totalPrice = orderItem.getPrice().doubleValue() * orderItem.getNum();
                        orderItem.setTotalFee(new BigDecimal(totalPrice));
                    }

                } else {
                    // 2.1.4 如果数据不存在，就让它追加到此商家的商品后面
                    orderItem = new TbOrderItem();
                    orderItem = createOrderItem(num,tbItem,orderItem);
                    // 2.1.5 添加到购物车中
                    tbOrderItemList.add(orderItem);
                }
            } else {
                // 2.2.1 如何购物车没有商家
                // 2.2.2 就让它自己去添加一个cats对象，并添加到集合中
                Carts cart = new Carts();
                cart.setSellerId(tbItem.getSellerId());
                cart.setSellerName(tbItem.getSeller());
                List<TbOrderItem> orderItemList = new ArrayList<TbOrderItem>();
                TbOrderItem orderItem = new TbOrderItem();
                orderItem = createOrderItem(num,tbItem,orderItem);
                orderItemList.add(orderItem);
                cart.setTbOrderItemList(orderItemList);
                // 2.2.3 在把car对象放到cartsList中
                cartsList.add(cart);
            }
            return cartsList;
        } else {
            throw new RuntimeException("无此商品");
        }
    }

    private TbOrderItem createOrderItem(int num, TbItem tbItem, TbOrderItem orderItem) {
        if (num < 1) {
            throw new RuntimeException("数量非法");
        }
        orderItem.setNum(num);
        orderItem.setGoodsId(tbItem.getGoodsId());
        // orderItem.setId();
        orderItem.setItemId(tbItem.getId());
        // orderItem.setOrderId();
        orderItem.setPicPath(tbItem.getImage());
        orderItem.setPrice(tbItem.getPrice());
        orderItem.setSellerId(tbItem.getSellerId());
        orderItem.setTitle(tbItem.getTitle());
        double totalPrice = orderItem.getPrice().doubleValue() * orderItem.getNum();
        orderItem.setTotalFee(new BigDecimal(totalPrice));
        return orderItem;
    }

    /**
     * 判断即将添加的商品是否存在购物车中
     * @param itemId
     * @param tbOrderItemList
     * @return
     */
    private TbOrderItem findCartToOrderItem(Long itemId, List<TbOrderItem> tbOrderItemList) {
        for (TbOrderItem tbOrderItem : tbOrderItemList) {
            if (itemId.longValue() == tbOrderItem.getItemId().longValue()) {
                return tbOrderItem;
            }
        }
        return null;
    }

    /**
     * 判断添加到购物车这件商品是否有自己的商家
     * @param sellerId
     * @param cartsList
     * @return
     */
    private Carts findCartToSellerShopping(String sellerId,List<Carts> cartsList) {
        for (Carts carts : cartsList) {
            if (sellerId.equals(carts.getSellerId())){
                return carts;
            }
        }
        return null;
    }
    @Override
    public void saveCartListToRedis(String sessionId, List<Carts> cartsList) {
        String string = JSON.toJSONString(cartsList);
        redisTemplate.boundValueOps(sessionId).set(string,48l, TimeUnit.HOURS);
    }

    @Override
    public void saveCartListToRedisByUsername(String username, List<Carts> cartList) {
        String string = JSON.toJSONString(cartList);//"[{},{}]"
        redisTemplate.boundValueOps(username).set(string);
    }
    @Override
    public List<Carts> mergeCartList(List<Carts> cartsList_sessionId, List<Carts> cartsList_userName) {
        for (Carts carts : cartsList_sessionId) {
            for (TbOrderItem orderItem : carts.getTbOrderItemList()) {
                cartsList_userName = addCartToNewList(cartsList_userName,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return cartsList_userName;
    }

    @Override
    public void delCartItemSession(List<Carts> cartsList_sessionId) {
        redisTemplate.delete(cartsList_sessionId);
    }
}
