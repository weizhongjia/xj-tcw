package com.msh.tcw.config;

import com.msh.tcw.core.ProjectConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

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
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name(ProjectConstant.JWT_HEADER).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("同城网后台")
                .apiInfo(apiInfo("同程网后台"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.msh.tcw.controller.admin"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description(title + "API")
                .version("1.0")
                .build();
    }
}