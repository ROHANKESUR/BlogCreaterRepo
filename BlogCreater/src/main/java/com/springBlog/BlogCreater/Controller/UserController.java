package com.springBlog.BlogCreater.Controller;

import com.springBlog.BlogCreater.Entity.Users;
import com.springBlog.BlogCreater.Request.LoginRequest;
import com.springBlog.BlogCreater.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

   @Autowired
   UserService userService;

   @PostMapping("/addUser")
   @CrossOrigin(origins = "http://localhost:5173")
   public Users addUser( @RequestBody Users user ){

      return userService.addUser( user );
   }

   @PostMapping ( "/loginUser" )
   @CrossOrigin(origins = "http://localhost:5173")
   public ResponseEntity<String> loginUser ( @RequestBody LoginRequest loginRequest ) {
      String token = userService.loginUser( loginRequest);
      System.out.println(token);
      return ResponseEntity.ok(token);
   }
}
