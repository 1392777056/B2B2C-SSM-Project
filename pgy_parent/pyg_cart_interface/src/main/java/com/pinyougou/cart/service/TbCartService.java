package com.pinyougou.cart.service;

import domainGroup.Carts;

import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/30 10:38
 */
public interface TbCartService {

    List<Carts> findCartToListRedis(String sessionId);

    List<Carts> addCartToNewList(List<Carts> cartsList, Long itemId, int num);

    void saveCartListToRedis(String sessionId, List<Carts> cartsList);
    void saveCartListToRedisByUsername(String username, List<Carts> cartsList);

    List<Carts> mergeCartList(List<Carts> cartsList_sessionId, List<Carts> cartsList_userName);

    void delCartItemSession(List<Carts> cartsList_sessionId);
}
