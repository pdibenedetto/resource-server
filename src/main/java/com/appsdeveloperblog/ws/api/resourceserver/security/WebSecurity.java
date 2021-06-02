package com.appsdeveloperblog.ws.api.resourceserver.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

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

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/status/check")
                // .hasAuthority(AUTHORITY_PROFILE)
                .hasRole(ROLE_DEVELOPER)    // do not attach ROLE_ prefix
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
