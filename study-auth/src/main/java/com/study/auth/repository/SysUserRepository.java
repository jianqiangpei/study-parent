package com.study.auth.repository;

import com.study.auth.domain.entity.SysUserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUserEntity , Integer> {


    SysUserEntity findFirstByUsername(String username);


}
