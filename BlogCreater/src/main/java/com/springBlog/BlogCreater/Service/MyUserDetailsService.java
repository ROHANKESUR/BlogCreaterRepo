package com.springBlog.BlogCreater.Service;

import com.springBlog.BlogCreater.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

   @Autowired
   UserRepo userRepo;
   @Override
   public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
      return userRepo.findById(username)
              .map(user -> org.springframework.security.core.userdetails.User.builder()
                      .username(user.getEmail())
                      .password(user.getPassword())
                      .roles("USER")
                      .build()
              )
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
   }

}
