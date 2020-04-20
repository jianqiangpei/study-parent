package com.study.auth.config;

import com.study.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * security 基础配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    /**
     * password 验证需要设置
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        // 调用服务里面的一个方法得到一个安全管理
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        // 初次学习，先将用户放到内存里面，之后把他再放到数据库维护
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        super.configure(auth);

    }

//
//
//
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails user1 = new User("admin" , "123456" , new HashSet<>());
//        UserDetails user2 = new User("alex" , "123456" , new HashSet<>());
//        inMemoryUserDetailsManager.createUser(user1);
//        inMemoryUserDetailsManager.createUser(user2);
//
//        return inMemoryUserDetailsManager;
//    }

    /**
     * 拦截配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf，拦截所有请求
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated();
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {

//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }


}
