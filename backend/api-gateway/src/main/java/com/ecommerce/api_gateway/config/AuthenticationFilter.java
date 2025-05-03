package com.ecommerce.api_gateway.config;

import com.ecommerce.api_gateway.service.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Autowired
    public JwtValidator jwtValidator;

    @Autowired
    public RouteValiditor validator;
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain)->{
            System.out.println("In Authentication Filter :"+exchange.getRequest().getCookies());
            System.out.println(exchange.getRequest());
            if(validator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Authentication Header missing.!!!");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token;

                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    token = authHeader.substring(7);
                    System.out.println("Token in api gateway:" + token);
                }else{
                    throw new RuntimeException("Something went wrong.");
                }
                try{
                    jwtValidator.validateToken(token);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
