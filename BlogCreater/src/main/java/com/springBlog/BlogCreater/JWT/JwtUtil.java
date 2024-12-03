package com.springBlog.BlogCreater.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

   private static final String secret = "huhfhufiuhfhuhukebhbufifhuuhfhuhfhufiuhfhuhukebhbufifhuuhf";
   private static final SecretKey H_MAC_Key = Keys.hmacShaKeyFor( secret.getBytes() );


   public String generateToken ( String username ) {
      Map<String, Object> claims = new HashMap<>();

      return Jwts.builder()
              .setClaims( claims )
              .setSubject( username )
              .setIssuedAt( new Date( System.currentTimeMillis() ) )
              .setExpiration( new Date( System.currentTimeMillis() + 60 * 60 * 30 ) )
              .signWith(H_MAC_Key )
              .compact();
   }


   public String extractUserName ( String token ) {
      return extraxtClaims(token, Claims::getSubject );
   }

   private <T> T  extraxtClaims ( String token, Function<Claims,T> claimSolver  ) {
      final Claims claims = extractAllClaims(token);
      return claimSolver.apply(claims);
   }

   private Claims extractAllClaims ( String token ) {
      return Jwts.parserBuilder()
              .setSigningKey( H_MAC_Key )
              .build()
              .parseClaimsJws( token )
              .getBody();
   }


   public boolean validateToken ( String token, UserDetails userDetails ) {
      final String username = extractUserName( token );
      return (username.equals( userDetails.getUsername()) && !isTokenExpired(token) );
   }

   private boolean isTokenExpired ( String token ) {
      return extractExpiration(token).before(new Date());

   }

   private Date extractExpiration ( String token ) {
      return extraxtClaims(token,Claims::getExpiration);
   }

}
