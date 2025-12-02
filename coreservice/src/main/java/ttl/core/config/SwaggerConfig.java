package com.ttl.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ttl.common.constant.ITag;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String bearerScheme = "bearerAuth";
        final String cookieScheme = "cookieAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version("v1")
                        .description("API với Bearer Token hoặc Cookie Session"))
                .addSecurityItem(new SecurityRequirement().addList(bearerScheme))
                .addSecurityItem(new SecurityRequirement().addList(cookieScheme))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(bearerScheme,
                                new SecurityScheme()
                                        .name(bearerScheme)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                        .addSecuritySchemes(cookieScheme,
                                new SecurityScheme()
                                        .name("JSESSIONID") // tên cookie thực tế
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                        )
                        .addSecuritySchemes(cookieScheme,
                                new SecurityScheme()
                                        .name(ITag.REFRESH_TOKEN) // tên cookie thực tế
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                        )
                );
    }
}
