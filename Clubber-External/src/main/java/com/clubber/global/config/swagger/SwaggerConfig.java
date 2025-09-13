package com.clubber.global.config.swagger;


import static java.util.stream.Collectors.groupingBy;

import com.clubber.global.dto.ErrorResponse;
import com.clubber.common.exception.BaseErrorCode;
import com.clubber.common.exception.ErrorReason;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);
        return new OpenAPI().
            info(swaggerInfo()).
            servers(List.of(server))
            .components(authSetting())
            .addSecurityItem(new SecurityRequirement()
                .addList("Authorization")
                .addList("cookieAuth"));
    }

    @Bean
    public GroupedOpenApi AllApi() {

        return GroupedOpenApi.builder()
            .group("0. 📌모든 API")
            .pathsToMatch("/api/v1/**")
            .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {

        return GroupedOpenApi.builder()
            .group("공지사항,FAQ API")
            .pathsToMatch("/api/v1/notices/**", "/api/v1/faqs")
            .build();
    }

    @Bean
    public GroupedOpenApi favoriteApi() {

        return GroupedOpenApi.builder()
            .group("즐겨찾기 API")
            .pathsToMatch("/api/v1/clubs/{clubId}/favorites/**")
            .build();
    }

    @Bean
    public GroupedOpenApi reviewApi() {

        return GroupedOpenApi.builder()
            .group("리뷰, 키워드 조회 API")
            .pathsToMatch("/api/v1/clubs/{clubId}/reviews/**", "/api/v1/keywords")
            .build();
    }

    @Bean
    public GroupedOpenApi recruitApi() {

        return GroupedOpenApi.builder()
            .group("모집글 조회 API")
            .pathsToMatch("/api/v1/recruits/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {

        return GroupedOpenApi.builder()
            .group("동아리 계정 API")
            .pathsToMatch("/api/v1/admins/**", "/api/v1/images/**")
            .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
            .group("인증, 회원 API")
            .pathsToMatch("/api/v1/auths/**", "/api/v1/users/**",
                "/api/v1/clubs/{clubId}/favorites/**")
            .build();
    }

    @Bean
    public GroupedOpenApi clubApi() {
        return GroupedOpenApi.builder()
            .group("정보 조회 API")
            .pathsToMatch("/api/v1/clubs/**")
            .pathsToExclude("/api/v1/clubs/{clubId}/favorites")
            .build();
    }

    @Bean
    public GroupedOpenApi EtcApi() {
        return GroupedOpenApi.builder()
            .group("그외 API")
            .pathsToMatch("/api/v1/example/**", "/api/v1/perspective")
            .build();
    }

    private Info swaggerInfo() {
        return new Info()
            .version("1.0")
            .title("Clubber API 문서")
            .description("Clubber의 API 문서입니다.");
    }

    private Components authSetting() {
        SecurityScheme cookieAuth = new SecurityScheme()
            .type(Type.APIKEY)
            .scheme(In.COOKIE.toString())
            .name("accessToken");

        SecurityScheme authorization = new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name("Authorization");

        return new Components()
            .addSecuritySchemes("cookieAuth", cookieAuth)
            .addSecuritySchemes("Authorization", authorization);
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            DisableSwaggerSecurity methodAnnotation = handlerMethod.getMethodAnnotation(
                DisableSwaggerSecurity.class);

            ApiErrorCodeExample apiErrorCodeExample =
                handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);

            if (methodAnnotation != null) {
                operation.setSecurity(Collections.emptyList());
            }

            if (apiErrorCodeExample != null) {
                generateErrorCodeExample(operation, apiErrorCodeExample.value());
            }
            return operation;

        };
    }

    private void generateErrorCodeExample(
        Operation operation, Class<? extends BaseErrorCode> type
    ) {
        ApiResponses responses = operation.getResponses();
        BaseErrorCode[] errorCodes = type.getEnumConstants();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolder = Arrays.stream(errorCodes)
            .map(
                baseErrorCode -> {
                    ErrorReason errorReason = baseErrorCode.getErrorReason();
                    return ExampleHolder.builder()
                        .holder(
                            getSwaggerExample(baseErrorCode.getErrorReason().getReason(),
                                baseErrorCode.getErrorReason())
                        )
                        .code(errorReason.getStatus())
                        .name(errorReason.getCode())
                        .build();
                }
            ).collect(groupingBy(ExampleHolder::getCode));
        addExamplesToResponses(responses, statusWithExampleHolder);
    }

    private Example getSwaggerExample(String value, ErrorReason errorReason) {
//ErrorResponse 는 클라이언트한 실제 응답하는 공통 에러 응답 객체입니다.
        ErrorResponse errorResponse = new ErrorResponse(errorReason, "요청시 패스정보입니다.");
        Example example = new Example();
        example.description(value);
        example.setValue(errorResponse);
        return example;
    }

    private void addExamplesToResponses(
        ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
            (status, v) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                // 상태 코드마다 ApiResponse을 생성합니다.
                ApiResponse apiResponse = new ApiResponse();
                //  List<ExampleHolder> 를 순회하며, mediaType 객체에 예시값을 추가합니다.
                v.forEach(
                    exampleHolder -> mediaType.addExamples(
                        exampleHolder.getName(), exampleHolder.getHolder()));
                // ApiResponse 의 content 에 mediaType을 추가합니다.
                content.addMediaType("application/json", mediaType);
                apiResponse.setContent(content);
                // 상태코드를 key 값으로 responses 에 추가합니다.
                responses.addApiResponse(status.toString(), apiResponse);
            });
    }
}





