package com.expense.management.app.expenseManagementApp.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public record GoogleTokenInfo (
    @JsonProperty("azp") String authorizedParty,
    @JsonProperty("aud") String audience,
    @JsonProperty("sub") String subject,
    @JsonProperty("scope") String scope,
    @JsonProperty("exp") long expirationTime,
    @JsonProperty("expires_in") int expiresIn,
    @JsonProperty("email") String email,
    @JsonProperty("email_verified") boolean emailVerified,
    @JsonProperty("access_type") String accessType,
    @JsonProperty("name") String name,
    @JsonProperty("given_name") String givenName,
    @JsonProperty("family_name") String familyName,
    @JsonProperty("picture") String picture,
    @JsonProperty("locale") String locale,
    @JsonProperty("iss") String issuer,
    @JsonProperty("iat") long issuedAt,
    @JsonProperty("at_hash") String accessTokenHash
) {


}
