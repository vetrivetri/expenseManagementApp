package com.expense.management.app.expenseManagementApp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Value("${spring.admin}")
    private String adminEmail;
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        List<GrantedAuthority> authorities = new ArrayList<>();
        //TO-DO take it from property File later
        if ("vetri129@gmail.com".equals(email)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }

}
