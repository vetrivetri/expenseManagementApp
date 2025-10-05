package com.expense.management.app.expenseManagementApp.security;

import com.expense.management.app.expenseManagementApp.beans.GoogleTokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class ExpenseManageAuthFilter extends OncePerRequestFilter {
    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/login/google","/",  "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/webjars/**",
            "/swagger-resources/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            validateIdToken(token);
    }
        String path = request.getRequestURI();
        if (EXCLUDED_PATHS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response); // Skip filter
            return;
        }


        filterChain.doFilter(request, response);
    }

    public GoogleTokenInfo validateIdToken(String idToken) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoogleTokenInfo> response = restTemplate.getForEntity(
                "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken,
                GoogleTokenInfo.class
        );
        return response.getBody();
    }

}
