package org.studyframework.swagger;

import com.google.common.base.Predicate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "study.swagger.instance")
@Data
public class SwaggerResourceProperties {

    private boolean enable = true;

    private final ApiInfo apiInfo = new ApiInfo();

    @Data
    public static class ApiInfo{

        /**
         * 文档标题
         */
        private String title;

        /**
         * 文档描述
         */
        private String description;

        /**
         * 文档版本
         */
        private String version;

        /**
         * termsOfServiceUrl
         */
        private String termsOfServiceUrl = "";

        private String license;

        private String licenseUrl;

        private String defaultIncludePattern;

        private List<VendorExtension> vendorExtensions = new ArrayList<>();

        private final Contact contact = new Contact();


        public Predicate<String> isIncludePath(){
            if(StringUtils.isEmpty(defaultIncludePattern))
                return PathSelectors.none();
            return PathSelectors.regex(this.defaultIncludePattern);
        }

    }

    /**
     * 文档作者，也可以理解为文档联系人
     */
    @Data
    public static class Contact{

        /**
         * 作者名称
         */
        private String contactName = "";

        /**
         * 作者链接或方式
         */
        private String contactUrl = "";

        /**
         * 作者邮箱
         */
        private String contactEmail = "";

    }

}
