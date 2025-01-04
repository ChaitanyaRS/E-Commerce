package com.ecommerce.userservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.ecommerce.userservice.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private com.ecommerce.userservice.service.JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Reached JWT filter");
        // String authHeader = request.getHeader("Authorization");
        String token = "";
        Cookie cookies = WebUtils.getCookie(request, "token");
        System.out.println("Cookie value from request :"+cookies);
        String username = null;

        if(cookies != null){
            token = cookies.getValue();
            System.out.println("Token value from Cookie :"+token);
            username = jwtService.extractUserName(token);
            if(username == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is invalid, relogin");
                return;
            }
            System.out.println("Username in JWT Filter " + username);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            System.out.println("Validate Token: "+ jwtService.validateToken(token, userDetails));
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
            }
        }
        filterChain.doFilter(request, response);
    }
}
