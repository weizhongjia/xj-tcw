package com.msh.tcw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class Swagger2 {

    @Bean
    public Docket createWxApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("同城网小程序前端")
                .apiInfo(apiInfo("同程网微信小程序"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.msh.tcw.controller.wx"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createAdminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("同城网后台")
                .apiInfo(apiInfo("同程网后台"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.msh.tcw.controller.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description(title + "API")
                .version("1.0")
                .build();
    }
}