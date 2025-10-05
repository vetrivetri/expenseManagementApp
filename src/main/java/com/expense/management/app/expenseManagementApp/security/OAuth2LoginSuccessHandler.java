package com.expense.management.app.expenseManagementApp.security;

import com.expense.management.app.expenseManagementApp.beans.CreateUserReq;
import com.expense.management.app.expenseManagementApp.beans.Oauth2ResponseBean;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

@Autowired
     RestClient restClient;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();
        String idToken = null;
        Oauth2ResponseBean oauth2Response = new Oauth2ResponseBean();
        if (client.getAccessToken() instanceof OAuth2AccessToken) {
            // id_token can be in the attributes of principal

            OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
            OidcIdToken idTokenForJwt = oidcUser.getIdToken();
             idToken = idTokenForJwt.getTokenValue();


            UserData data= addUserToSystem( new CreateUserReq(oidcUser.getFullName(),oidcUser.getEmail(),oidcUser.getSubject()),idToken);
            oauth2Response.setJwtAccesToken(idToken);
            oauth2Response.setUserEmail(data.getUserEmail());
            oauth2Response.setUserId(data.getUserPkId());
            oauth2Response.setUserName(data.getUserName());
        }

        // You can return accessToken or idToken depending on your need
        String tokenToSend = (idToken != null) ? idToken : accessToken;

        //Call Internal API to add USER before Sending Response


        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + new ObjectMapper().writeValueAsString(oauth2Response) + "\"}");
    }

    public UserData addUserToSystem(CreateUserReq req,String idToken) {

        return restClient.post()
                .uri("/v1/group/add-user")
                .body(req)
                .header("Authorization","Bearer " +idToken)
                .retrieve()
                .body(UserData.class);
    }


}

