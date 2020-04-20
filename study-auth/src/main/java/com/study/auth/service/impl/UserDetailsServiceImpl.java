package com.study.auth.service.impl;

import com.study.auth.domain.dto.UserDetailDTO;
import com.study.auth.domain.entity.SysAuthorityEntity;
import com.study.auth.domain.entity.SysUserEntity;
import com.study.auth.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {



    @Resource
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("密码模式查询用户信息");
        SysUserEntity sysUser = userService.selectByUsername(s);
        if (sysUser == null) {
            throw new UsernameNotFoundException("not found user:" + s);
        }
        UserDetailDTO userDetail = new UserDetailDTO();
        BeanUtils.copyProperties(sysUser,userDetail);

        //这里权限列表,这个为方便直接下（实际开发中查询用户时连表查询出权限）
        Set<SysAuthorityEntity> authoritySet = new HashSet<>();
        authoritySet.add(new SysAuthorityEntity("admin","管理员权限"));
        userDetail.setAuthorities(authoritySet);
        return userDetail;
     }

}
