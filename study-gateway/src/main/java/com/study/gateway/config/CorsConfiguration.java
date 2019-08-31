//package com.study.gateway.config;
//
//import com.study.gateway.filter.CorsResponseHeaderFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.DefaultCorsProcessor;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.util.pattern.PathPatternParser;
//
//@Configuration
//public class CorsConfiguration {
//
//    @Bean
//    public CorsResponseHeaderFilter corsResponseHeaderFilter() {
//        return new CorsResponseHeaderFilter();
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public CorsWebFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//        source.registerCorsConfiguration("/**", buildCorsConfiguration());
//
//        return new CorsWebFilter(source, new DefaultCorsProcessor() {
//            @Override
//            protected boolean handleInternal(ServerWebExchange exchange, org.springframework.web.cors.CorsConfiguration config , boolean preFlightRequest) {
//                return super.handleInternal(exchange, config, preFlightRequest);
//            }
//        });
//    }
//
//    private org.springframework.web.cors.CorsConfiguration buildCorsConfiguration() {
//        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//
//        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
//        corsConfiguration.addAllowedMethod(HttpMethod.POST);
//        corsConfiguration.addAllowedMethod(HttpMethod.GET);
//        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
//        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
//        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
//
////        corsConfiguration.addAllowedHeader("origin");
////        corsConfiguration.addAllowedHeader("content-type");
////        corsConfiguration.addAllowedHeader("accept");
////        corsConfiguration.addAllowedHeader("x-requested-with");
////        corsConfiguration.addAllowedHeader("Referer");
//         corsConfiguration.addAllowedHeader("*");
//
//        corsConfiguration.setMaxAge(7200L);
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
//
//}
