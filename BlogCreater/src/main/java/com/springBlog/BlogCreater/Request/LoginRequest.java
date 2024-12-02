package com.springBlog.BlogCreater.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

   private String userid;
   private String password;

   public LoginRequest () {

   }

   public LoginRequest ( String userid, String password ) {


      this.userid = userid;
      this.password = password;
   }


}
