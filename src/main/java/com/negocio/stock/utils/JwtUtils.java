package com.negocio.stock.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.negocio.stock.repository.ISellerRepository;
import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Autowired
    private ISellerRepository sellerRepository;

    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String username = authentication.getPrincipal().toString();

        Long sellerId = sellerRepository.findSellerIdByUsername(username)
                .orElseThrow(() -> new PropertyNotFoundException("No se encontro el id del vendedor."));

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim("sellerId",sellerId)
                .withClaim("authorities",authorities)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60000)))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                                        .withIssuer(userGenerator)
                                        .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token. Not authorized");
        }
    }

    public String getUsername(DecodedJWT token){
        return token.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT token, String claimName){
        return token.getClaim(claimName);
    }

    public Map<String,Claim> getClaims(DecodedJWT token){
        return token.getClaims();
    }
}
