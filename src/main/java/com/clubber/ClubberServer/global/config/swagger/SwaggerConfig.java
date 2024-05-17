package com.clubber.ClubberServer.global.config.swagger;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server().url("http://localhost:8080").description("local");
        Server devServer = new Server().url("http://15.164.211.56:8080").description("dev");
        return new OpenAPI().info(swaggerInfo()).servers(List.of(localServer, devServer));
    }

    private Info swaggerInfo(){
        return new Info()
                .version("1.0")
                .title("Clubber API 문서")
                .description("Clubber의 API 문서입니다.");
    }

}
