package org.studyframework.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Resource
    private SwaggerResourceProperties swaggerResourceProperties;

    @Bean
    public Docket swaggerSpringFoxApiDocket(){
        log.debug("starting swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                swaggerResourceProperties.getApiInfo().getContact().getContactName(),
                swaggerResourceProperties.getApiInfo().getContact().getContactUrl(),
                swaggerResourceProperties.getApiInfo().getContact().getContactEmail()
        );

        ApiInfo apiInfo = new ApiInfo(
                swaggerResourceProperties.getApiInfo().getTitle(),
                swaggerResourceProperties.getApiInfo().getDescription(),
                swaggerResourceProperties.getApiInfo().getVersion(),
                swaggerResourceProperties.getApiInfo().getTermsOfServiceUrl(),
                contact,
                swaggerResourceProperties.getApiInfo().getLicense(),
                swaggerResourceProperties.getApiInfo().getLicenseUrl(),
                swaggerResourceProperties.getApiInfo().getVendorExtensions()
        );

        ParameterBuilder tokenParameterBuilder = new ParameterBuilder();
        tokenParameterBuilder.name("X-AUTH").description("认证token").modelRef(new ModelRef("string")).parameterType("header").build();
        List<Parameter> parameterList = Collections.singletonList(tokenParameterBuilder.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerResourceProperties.isEnable())
                .apiInfo(apiInfo)
                .globalOperationParameters(parameterList)
                .forCodeGeneration(true)
                .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(swaggerResourceProperties.getApiInfo().isIncludePath())
                .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }


}
