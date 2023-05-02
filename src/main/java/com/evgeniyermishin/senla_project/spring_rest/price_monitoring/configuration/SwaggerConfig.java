package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Configuration
    public class SwaggerConfiguration {
        public static final String AUTHORIZATION_HEADER = "Authorization";

        private ApiInfo apiInfo() {
            return new ApiInfo("MyApp Rest APIs",
                    "APIs for MyApp.",
                    "1.0",
                    "",
                    new Contact("Created by Ermishin Evgeniy", "", ""),
                    "",
                    "",
                    Collections.emptyList());
        }

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(apiInfo())
                    .securityContexts(Arrays.asList(securityContext()))
                    .securitySchemes(Arrays.asList(apiKey()))
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();


        }

        private ApiKey apiKey() {
            return new ApiKey("Authorization", "Bearer", "header");
        }

        private SecurityContext securityContext() {
            return SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .build();

        }

        List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope
                    = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return Arrays.asList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
        }

    }
}
