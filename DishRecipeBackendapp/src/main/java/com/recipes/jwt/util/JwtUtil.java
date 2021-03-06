package com.recipes.jwt.util;



import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.recipes.controller.RecipeBackendController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	Logger logger = LoggerFactory.getLogger(JwtUtil.class); 
	  private String secret = "xadmin";

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	    	// here it will check if the token has created before time limit. i.e 10 hours then will return true else false
	        return extractExpiration(token).before(new Date());
	    }

	    // this method is for generating token. as argument is username. so as user first time send request with username and password
	    // so here we will fetch the username , so based on that username we are going to create one token
	   
	 // generate token for user
		public String generateToken(UserDetails userDetails) {
			Map<String, Object> claims = new HashMap<>();
			Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
			if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				claims.put("isAdmin", true);
			}
			if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
				claims.put("isUser", true);
			}
			return doGenerateToken(claims, userDetails.getUsername());
		}

	
	    // in this method createToken subject argument is username
	    // here we are setting the time for 10 hours to expire the token. 
	    // and you can see we are using HS256 algorithm
	    private String doGenerateToken(Map<String, Object> claims, String subject) {
	    	logger.info("JWT token generated successfuly");

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(SignatureAlgorithm.HS256, secret).compact();
	        
	    }

	    // here we are validation the token
	    public Boolean validateToken(String token, UserDetails userDetails) {
	    	 // basically token will be generated in encrypted string and from that string . we extract our username and password using extractUsername method
	        final String username = extractUsername(token);
	        // here we are validating the username and then check the token is expired or not
	        if(isTokenExpired(token)==true)
	        	logger.info("Input TOKEN got EXPIRED");
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	}
