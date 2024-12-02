package com.springBlog.BlogCreater.Configuration;

import com.springBlog.BlogCreater.Filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


   private final ApplicationContext applicationContext;

   public SecurityConfig( ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
   }

   @Bean
   public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity ) throws Exception {
      JwtFilter jwtFilter = applicationContext.getBean( JwtFilter.class );
      httpSecurity.csrf( AbstractHttpConfigurer :: disable )
              .authorizeHttpRequests(x -> x.requestMatchers( "/addUser","/loginUser" )
                      .permitAll().anyRequest()
                      .authenticated())
              .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
              .sessionManagement(s->s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ));
      return httpSecurity.build();

   }

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   @Bean
   UserDetailsService userDetailsService(){
      return username -> {
         throw new UnsupportedOperationException("custom user services not implemented"  );
      };
   }
}
