package com.springBlog.BlogCreater.Configuration;

import com.springBlog.BlogCreater.JWT.JwtFilter;
import com.springBlog.BlogCreater.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
   private JwtFilter jwtFilter;

   @Autowired
   private UserDetailsService userDetailsService;

   @Bean
   public SecurityFilterChain securityFilterChain ( HttpSecurity http ) throws Exception {
      http.authorizeHttpRequests( req -> req
              .requestMatchers( "/api/register","/api/login" ).permitAll()
              .requestMatchers( "/api/**" ).hasRole( "ADMIN" )
              .anyRequest().authenticated() );
      http.sessionManagement(session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ));
      http.csrf( AbstractHttpConfigurer :: disable );
      http.addFilterBefore( jwtFilter,UsernamePasswordAuthenticationFilter.class );
      http.httpBasic();



      return http.build();

   }


   @Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setPasswordEncoder(passwordEncoder() );
      authenticationProvider.setUserDetailsService(userDetailsService);
      return authenticationProvider;
   }

   @Bean
   PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder(12);
   }

   @Bean
   public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration )throws Exception{
      return configuration.getAuthenticationManager();
   }
}
