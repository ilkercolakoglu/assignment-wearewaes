package com.waes.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Provides swagger api documentation
 *
 * @developer -- ilkercolakoglu
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String ENDPOINTS_PACKAGE = "com.waes.assignment.controller";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(ENDPOINTS_PACKAGE))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .contact(new Contact("İlker Çolakoğlu", "https://github.com/ilkercolakoglu", "colakogluilker@gmail.com"))
                .title("Diff Service")
                .description("Service that provides comparison of two base 64 encoded JSON.")
                .build();
    }
}
