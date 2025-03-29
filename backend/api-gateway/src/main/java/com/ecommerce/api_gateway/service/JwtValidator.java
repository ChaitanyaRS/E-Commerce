package com.ecommerce.api_gateway.service;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtValidator {

    private String secretKey;

    public JwtValidator() {
        secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    }


    public void validateToken(String token) throws SignatureException, ExpiredJwtException, Exception {
        try {
            // Parse the JWT token and validate the signature
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())  // Set the secret key used for signing the JWT
                    .parseClaimsJws(token)     // Parse and validate the JWT
                    .getBody();                // Extract the claims (payload)
            System.out.println(claims);
            // Check if the token has expired
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                throw new ExpiredJwtException(null, claims, "Token has expired");
            }

        } catch (SignatureException e) {
            // This exception is thrown when the JWT signature is invalid
            throw new SignatureException("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            // This exception is thrown when the JWT token has expired
            throw new ExpiredJwtException(null, null, "Token has expired");
        } catch (MalformedJwtException e) {
            // This exception is thrown when the JWT token structure is malformed
            throw new MalformedJwtException("Invalid JWT token structure");
        } catch (UnsupportedJwtException e) {
            // This exception is thrown when the JWT uses an unsupported signing algorithm
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (Exception e) {
            // This is a generic exception for any other errors during token validation
            throw new Exception("Invalid JWT token");
        }
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
