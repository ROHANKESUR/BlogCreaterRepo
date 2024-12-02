package com.springBlog.BlogCreater.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Users {
   @Id
   private String email;
   private String name;
   private String password;

   public Users () {

   }
}


