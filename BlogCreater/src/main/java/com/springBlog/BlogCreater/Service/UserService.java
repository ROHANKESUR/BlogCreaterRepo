package com.springBlog.BlogCreater.Service;

import com.springBlog.BlogCreater.Entity.Users;
import com.springBlog.BlogCreater.Repository.UserRepo;
import com.springBlog.BlogCreater.Request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

   @Autowired
   UserRepo userRepo;

   @Autowired
   JWTService jwtService;

   @Autowired
   PasswordEncoder passwordEncoder;

   public Users addUser(Users user){

      user.setPassword( passwordEncoder.encode( user.getPassword() ) );
      return userRepo.save( user );
   }

   public String loginUser( LoginRequest loginRequest ){

      Optional<Users> user = userRepo.findById( loginRequest.getUserid() );

      if(user.isPresent() && passwordEncoder.matches( loginRequest.getPassword(),user.get().getPassword() )){

         return jwtService.generateToken( loginRequest.getUserid() );
      }else {
         throw new RuntimeException("invalid credentials");
      }
   }
}
