package com.negocio.stock.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtils {
    public String createToken(){
        return null;
    }
    public DecodedJWT decodeToken(String token){
        return null;
    }
    public Claim getSpecificClaim(DecodedJWT token, String claimName){
        return null;
    }
    public Map<String,Claim> getClaims(DecodedJWT token){
        return null;
    }
}
