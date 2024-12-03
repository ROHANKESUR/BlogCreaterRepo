package com.springBlog.BlogCreater.Service;

import com.springBlog.BlogCreater.Entity.Users;
import com.springBlog.BlogCreater.JWT.JwtUtil;
import com.springBlog.BlogCreater.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   @Autowired
   private UserRepo userRepo;

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private JwtUtil jwtUtil;

   public Users register(Users user){
      return userRepo.save( user );
   }

   public String verify ( Users user ) {
      Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(),user.getPassword() ) );
      if(authentication.isAuthenticated()){
         return jwtUtil.generateToken(user.getUsername());
      }

      return "fail";
   }
}
