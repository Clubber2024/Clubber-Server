package com.clubber.ClubberServer.global.config.swagger;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server().url("http://localhost:8080").description("local");
        Server devServer = new Server().url("http://15.164.211.56:8080").description("dev");
        return new OpenAPI().
                info(swaggerInfo()).
                servers(List.of(localServer, devServer))
                .components(authSetting())
                .addSecurityItem(new SecurityRequirement()
                        .addList("Authorization"));
    }

    private Info swaggerInfo(){
        return new Info()
                .version("1.0")
                .title("Clubber API 문서")
                .description("Clubber의 API 문서입니다.");
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes("Authorization",
                        new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(In.HEADER)
                                .name("Authorization"));
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation opeartion, HandlerMethod handlerMethod) -> {
            DisableSwaggerSecurity methodAnnotation = handlerMethod.getMethodAnnotation(
                    DisableSwaggerSecurity.class);
            if(methodAnnotation != null) {
                opeartion.setSecurity(Collections.emptyList());
            }
            return opeartion;
            };
        }
    }


