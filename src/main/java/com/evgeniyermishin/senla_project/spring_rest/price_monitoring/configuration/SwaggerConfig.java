package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "application.swagger.enabled", havingValue = "true")
public class SwaggerConfig {

    @Value("${application.swagger.title}")
    private String swaggerTitle;

    @Value("${application.swagger.description}")
    private String swaggerDescription;

    @Value("${application.swagger.version}")
    private String swaggerVersion;

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo(
                swaggerTitle,
                swaggerDescription,
                swaggerVersion,
                "",
                new Contact("Evgeniy", "", "bankok96@gmail.com"),
                "",
                "",
                new ArrayList<>()
        );
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
