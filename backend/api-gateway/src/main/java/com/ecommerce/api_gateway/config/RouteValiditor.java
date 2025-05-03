package com.ecommerce.api_gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValiditor {

    public static final List<String> openApiEndPoints = List.of(
            "/user/login",
            "/user/register"
//            "/user/confirm"
    );

    Predicate<ServerHttpRequest> isSecured =
            request-> openApiEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
