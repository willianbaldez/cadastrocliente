package br.com.mvp.cadastroclienteapi.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({"dev", "test","default"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${app.build.version:}")
    private String appBuildVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("br.com.mvp.cadastroclienteapi.controller"))
            .paths(PathSelectors.any()).build()
            .apiInfo(apiInfo())
            .securityContexts(Arrays.asList(actuatorSecurityContext()));
    }

    private SecurityContext actuatorSecurityContext() {
        return SecurityContext.builder()
            .forPaths(PathSelectors.ant("/actuator/**"))
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Projeto de Cadastro de Cliente")
            .description("Processo seletivo desenvolvimento java")
            .version(appBuildVersion)
            .build();
    }
}
