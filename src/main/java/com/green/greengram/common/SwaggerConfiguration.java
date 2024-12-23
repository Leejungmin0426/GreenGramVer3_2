package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.security.config.annotation.web.SecurityMarker;


@OpenAPIDefinition(
    info = @Info(
        title = "GreenGram",
        version = "v2",
        description = "그린그램 SNS"
    )
        , security = @SecurityRequirement(name = "Authorization")
)

@SecurityScheme(
        type = SecuritySchemeType.HTTP
        , name = "Authorization"
        , in = SecuritySchemeIn.HEADER
        , bearerFormat = "JWT"
        , scheme = "Bearer"
) // Swagger에서도 인증처리 가능하도록! 추가추가

public class SwaggerConfiguration {}
