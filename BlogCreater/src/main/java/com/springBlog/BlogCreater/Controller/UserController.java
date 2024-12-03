package com.springBlog.BlogCreater.Controller;

import com.springBlog.BlogCreater.Entity.Users;
import com.springBlog.BlogCreater.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {


   @Autowired
   private UserService userService;

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


   @PreAuthorize( "hasRole('ADMIN')")
   @GetMapping("/hello")
   public String sayHello(){
      return "Hello";
   }

   @PostMapping("/register")
   public Users register( @RequestBody Users user ){
      user.setPassword( encoder.encode( user.getPassword() ) );
      return userService.register( user );
   }

   @PostMapping("/login")
      public String login( @RequestBody Users user ){
         return userService.verify(user);
      }
   }

