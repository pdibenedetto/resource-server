package com.appsdeveloperblog.ws.api.resourceserver.controllers;

import com.appsdeveloperblog.ws.api.resourceserver.model.UserRest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status() {
        return "Working...";
    }

    @PostAuthorize("hasRole('ADMIN') or returnObject.id == jwt.subject")
    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {

        return UserRest.builder()
                .firstName("Paul")
                .lastName("DiBenedetto")
                .id(UUID.fromString("c4df74b7-051b-4c0d-b6f2-e991c46187c0").toString())
                .build();
    }

    // @Secured("ROLE_developer")
    // @PreAuthorize("hasRole('developer')")
    @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
    // This enables that the user can delete their own user record
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return "Deleted user with id " + id + " and Jwt subject " + jwt.getSubject();
    }


}
