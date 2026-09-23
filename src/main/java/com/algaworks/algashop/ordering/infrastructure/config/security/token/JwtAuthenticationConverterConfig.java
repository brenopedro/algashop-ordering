package com.algaworks.algashop.ordering.infrastructure.config.security.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class JwtAuthenticationConverterConfig {

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(
            JwtGrantedAuthoritiesDelegatingConverter jwtGrantedAuthoritiesDelegatingConverter) {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesDelegatingConverter);
        return jwtAuthenticationConverter;

    }
}
