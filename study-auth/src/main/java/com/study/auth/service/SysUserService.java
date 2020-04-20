package com.study.auth.service;

import com.study.auth.domain.entity.SysUserEntity;

public interface SysUserService {


    SysUserEntity selectByUsername(String username);


}
