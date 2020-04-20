package com.study.auth.service.impl;

import com.study.auth.domain.entity.SysUserEntity;
import com.study.auth.repository.SysUserRepository;
import com.study.auth.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserRepository sysUserRepository;

    /**
     *  根据用户名查询用户
     * @param username
     * @return cn.poile.ucs.auth.entity.SysUser
     */
    public SysUserEntity selectByUsername(String username) {


        return sysUserRepository.findFirstByUsername(username);
    }



}
