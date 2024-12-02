package com.springBlog.BlogCreater.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
   Key key = Keys.secretKeyFor( SignatureAlgorithm.HS256 );

   Map <String,Object> claims = new HashMap<>();


   public String generateToken(String username){
      return Jwts.builder()
              .setSubject( username )
              .setIssuedAt(new Date( System.currentTimeMillis()))
              .setExpiration( new Date(System.currentTimeMillis()+60*60*30) )
              .signWith( key, SignatureAlgorithm.HS256 )
              .compact();

   }

   public String extractUsername(String token) {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
         return true;
      } catch ( JwtException | IllegalArgumentException e) {
         return false;
      }
   }

   public boolean validateToken(String token, UserDetails userDetails) {
      String username = extractUsername(token);
      return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
   }

   private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   private Date extractExpiration(String token) {
      return Jwts.parserBuilder().setSigningKey(key).build()
              .parseClaimsJws(token).getBody().getExpiration();
   }



}
