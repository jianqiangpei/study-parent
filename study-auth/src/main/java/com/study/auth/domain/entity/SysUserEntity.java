package com.study.auth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    private String mobile;

    private String email;

    private String password;
}
