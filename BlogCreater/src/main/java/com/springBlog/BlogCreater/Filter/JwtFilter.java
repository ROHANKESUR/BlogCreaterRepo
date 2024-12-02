package com.springBlog.BlogCreater.Filter;

import com.springBlog.BlogCreater.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

   private final JWTService jwtService;
   private final UserDetailsService userDetailsService;

   public JwtFilter ( JWTService jwtService, UserDetailsService userDetailsService ) {
      this.jwtService = jwtService;
      this.userDetailsService = userDetailsService;
   }



   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
           throws ServletException, IOException {

      String authHeader = request.getHeader("Authorization");

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
         String token = authHeader.substring(7);

         if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);

               if (jwtService.validateToken(token, userDetails)) {
                  UsernamePasswordAuthenticationToken authentication =
                          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  SecurityContextHolder.getContext().setAuthentication(authentication);
               }
            }
         }
      }

      filterChain.doFilter(request, response);
   }
}
