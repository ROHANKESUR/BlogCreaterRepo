package com.springBlog.BlogCreater.Service;

import com.springBlog.BlogCreater.Entity.UserPrincipal;
import com.springBlog.BlogCreater.Entity.Users;
import com.springBlog.BlogCreater.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {

   @Autowired
   private UserRepo userRepo;

   @Override
   public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {

      Users user = userRepo.findByUsername( username );

      if(user == null){
         throw new UsernameNotFoundException( "User not found" + username);
      }
      return new UserPrincipal(user);
   }
}
