package com.study.auth.config;

import com.study.auth.converter.CustJwtAccessTokenConverter;
import com.study.auth.handle.CustomAccessDeniedHandler;
import com.study.auth.handle.CustomAuthEntryPoint;
import com.study.auth.handle.CustomWebResponseExceptionTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * oauth2 配置文件
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {


    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private DataSource dataSource;

    @Resource
    private CustomAuthEntryPoint customAuthEntryPoint;

    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Resource
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;


    /**
     * 配置客户端详情信息(Client Details)
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     *
     * 配置第三方应用
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients
                .withClientDetails(new JdbcClientDetailsService(dataSource));

        log.info("ClientDetailsServiceConfigurer is complete!");
    }


    /**
     * token的存储方式
     * @param endpoints {@link AuthorizationServerEndpointsConfigurer}
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints
                // 如果使用密码模式，必须配置 authenticationManager
                // authenticationManager的配置在SecurityConfigurerAdapter这里
                .authenticationManager(authenticationManager)
                // 将token存储在哪里
//                .tokenServices(tokenServices())

                //如果使用jwt token，那么下面这两行必须配置 tokenStore和accessTokenConverter
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .exceptionTranslator(customWebResponseExceptionTranslator);

        log.info("AuthorizationServerEndpointsConfigurer is complete.");
    }


    /**
     * 哪些人可以访问
     * @param security {@link AuthorizationServerSecurityConfigurer}
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                // 允许所有访问，默认是拒绝所有人访问
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(customAuthEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
        log.info("AuthorizationServerSecurityConfigurer is complete");
    }


//    /**
//     * redis存储方式
//     *
//     */
//    @Bean("redisTokenStore")
//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    /**
//     * 配置生成token的有效期以及存储方式（此处用的redis）
//     *
//     */
//    @Bean
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(redisTokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30));
//        defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
//        return defaultTokenServices;
//    }

    @Bean
    public TokenStore jwtTokenStore(){

        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("study-jwt.jks"), "study-jwt".toCharArray());
        CustJwtAccessTokenConverter tokenConverter = new CustJwtAccessTokenConverter();
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("study-jwt"));

        return tokenConverter;
    }
}
