package com.integrated.techhub.common.config;

import com.integrated.techhub.common.auth.jwt.JwtProvider;
import com.integrated.techhub.common.auth.resolver.AuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.http.HttpHeaders.LOCATION;

@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOW_ALL_PATH = "/**";
    private static final String ALLOWED_METHODS = "*";
    private static final String FRONTEND_LOCALHOST = "http://localhost:3000";

    private final JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALLOW_ALL_PATH)
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins(FRONTEND_LOCALHOST)
                .exposedHeaders(LOCATION);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(jwtProvider));
    }

}
