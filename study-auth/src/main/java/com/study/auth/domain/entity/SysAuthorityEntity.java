package com.study.auth.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "authority")
public class SysAuthorityEntity implements GrantedAuthority {

    /**
     * 权限
     */
    private String authority;

    /**
     * 权限描述
     */
    private String desc;
}
