package com.leonteqseucurity.com.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {
    private static String SECRETE_KEY="25432A462D4A404E635266556A586E3272357538782F413F4428472B4B625065";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);

    }
//    extract single clain
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    todo generate token
    public  String generateToken(
            Map<String,Object> extractClaims,
            UserDetails userDetails
    )
    {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//generating a token without extraClaims
    public  String generateToken(UserDetails userDetails)
    {
        return  generateToken(new HashMap<>(),userDetails);
    }
//    todo checking the validatity of token
    public  boolean isTokenValid(String token,UserDetails userDetails)
    {
        final  String username=extractUsername(token);



        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return exractExpiration(token).before(new Date());
    }

    private Date exractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);

    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
