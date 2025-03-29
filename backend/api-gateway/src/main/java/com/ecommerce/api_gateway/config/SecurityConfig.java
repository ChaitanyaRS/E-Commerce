package com.ecommerce.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic ->basic.disable())  // Disable HTTP Basic Authentication
                .formLogin(login ->login.disable())  // Disable the default login form
                .logout(logoutSpec -> logoutSpec.disable())// Disable CSRF
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/user/login", "/user/register").permitAll() // Allow only these endpoints
                        .anyExchange().authenticated()
                        // Restrict all other routes
                );

        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:5173")); // Vite app URL
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(corsConfigurationSource());
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Replace with your React Vite app URL
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

//---------------------------------------------------------------------

//package com.ecommerce.api_gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig implements WebFluxConfigurer {
//
////    @Bean
////    public SecurityWebFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
////        // Disable CSRF protection for Spring WebFlux application
////        http.csrf(customize -> customize.disable()); // Allow all endpoints to be accessed
////        return http.build();
////    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Add CORS configuration for specific endpoints or all endpoints
//        registry.addMapping("/**")  // Apply CORS configuration to all endpoints
//                .allowedOrigins("http://localhost:5173")  // Allow your React app URL (local)
//                .allowedMethods("GET", HttpMethod.POST.name(), "PUT", "DELETE", "OPTIONS")  // Allow HTTP methods
//                .allowedHeaders(HttpHeaders.CONTENT_TYPE,HttpHeaders.AUTHORIZATION,HttpHeaders.ACCEPT)  // Allow all headers
//                .allowCredentials(true);  // Allow cookies and credentials
//    }
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("http://localhost:5173"); // React app URL (local)
//        corsConfig.addAllowedMethod("GET");
//        corsConfig.addAllowedMethod(HttpMethod.POST);
//        corsConfig.addAllowedMethod("PUT");
//        corsConfig.addAllowedMethod("DELETE");
//        corsConfig.addAllowedMethod("OPTIONS");
//        corsConfig.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
//        corsConfig.addAllowedHeader(HttpHeaders.AUTHORIZATION);
//        corsConfig.addAllowedHeader(HttpHeaders.ACCEPT);
//        corsConfig.setAllowCredentials(true); // Allow cookies and credentials
//
//        // Create a CorsWebFilter with the configuration
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig); // Apply CORS configuration to all endpoints
//
////        CorsWebFilter corsWebFilter = new CorsWebFilter(source);
//        return new CorsWebFilter(source);
//    }
////
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
//        http.cors(Customizer.withDefaults()).csrf(customize -> customize.disable());  // Protect other endpoints
//        return http.build(); // Allow all endpoints to be accessed
////        return http.build();
//    }
//}
