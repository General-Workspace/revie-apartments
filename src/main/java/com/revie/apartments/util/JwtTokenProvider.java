package com.revie.apartments.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliseconds;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);


        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expireDate)
                .signWith(key(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

/*
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliseconds;

    private final Supplier<SecretKeySpec> getKey =() -> {
        Key key = Keys.hmacShaKeyFor(jwtSecret
                .getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
    };

    private final Supplier<Date> expirationTime = ()-> Date
            .from(LocalDateTime.now()
                    .plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant());
    private <T> T extractClaims(String token, Function<Claims, T> claimResolver){
        final Claims claims = Jwts.parser()
                .verifyWith(getKey.get())
                .build().parseSignedClaims(token).getPayload();
        return claimResolver.apply(claims);
    }

    public Function<String, String> extractUsername = token->
            extractClaims(token, Claims::getSubject);

    private final Function<String, Date> extractExpirationDate = token->
            extractClaims(token, Claims::getExpiration);

    public Function<String, Boolean> isTokenExpired = token->extractExpirationDate.apply(token)
            .after(new Date(System.currentTimeMillis()));

    public BiFunction<String, String, Boolean> isTokenValid = (token, username) ->
            isTokenExpired.apply(token)&& Objects.equals(extractUsername.apply(token), username);

    public Function<UserDetails, String> createJwt = userDetails -> {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .signWith(getKey.get())
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime.get())
                .compact();
    };
}
*/