package com.pinyougou.shop.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/14 17:57
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private TbSellerService tbSellerService;

    public void setTbSellerService(TbSellerService tbSellerService) {
        this.tbSellerService = tbSellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbSeller tbSeller = tbSellerService.findBySellerId(username);
        if (tbSeller ==null) {
            return null;
        }
        List<GrantedAuthority> authorities = null;
        if("1".equals(tbSeller.getStatus())){
            authorities = new ArrayList<GrantedAuthority>();
            SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(role_admin);
        }
        return new User(username,tbSeller.getPassword(),authorities);
    }

}
