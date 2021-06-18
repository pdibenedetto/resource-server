package com.appsdeveloperblog.ws.api.resourceserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// Equivalent to @Secured, enbles @Pre and @PostAuthorize
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final static String AUTHORITY_PROFILE = "SCOPE_profile";
    private final static String ROLE_DEVELOPER = "developer";
    private final static String AUTHORITY_DEVELOPER = "ROLE_developer";


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http
//                .cors()
//                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/status/check")
                // .hasAuthority(AUTHORITY_PROFILE)
                .hasRole(ROLE_DEVELOPER)    // do not attach ROLE_ prefix
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }

    // This bean and configuration is needed if we don't go to a Gateway but is directly connected.
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(List.of("*"));
//        corsConfiguration.setAllowedHeaders(List.of("*"));
//        corsConfiguration.setAllowedMethods(List.of("GET","POST"));
//
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//
//        return urlBasedCorsConfigurationSource;
//    }
}
