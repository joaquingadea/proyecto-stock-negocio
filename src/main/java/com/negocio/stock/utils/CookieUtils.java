package com.negocio.stock.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    public void addHttpOnlyCookie(String name, String value, int maxAge, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(name,value)
            .httpOnly(true)
            .secure(true) // en produccion true
            .path("/")
            .maxAge(maxAge)
            .sameSite("None")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteCookie(String name, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(name,null)
            .httpOnly(true)
            .secure(true) // en prod -> true
            .path("/")
            .maxAge(0)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
