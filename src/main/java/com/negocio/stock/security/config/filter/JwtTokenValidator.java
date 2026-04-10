package com.negocio.stock.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.negocio.stock.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        String jwt = getJwt(request);
        try {
            if(jwt != null) {
                DecodedJWT decodedJwt = jwtUtils.decodeToken(jwt);

                String username = jwtUtils.getUsername(decodedJwt);
                String authorities = decodedJwt.getClaim("authorities").asString();


                if (authorities == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Collection<? extends GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorityList);
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
    }

    public String getJwt(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,"jwt");
        return cookie != null ? cookie.getValue() : null;
    }
}
