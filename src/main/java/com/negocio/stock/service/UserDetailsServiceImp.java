package com.negocio.stock.service;

import com.negocio.stock.dto.AuthLoginRequestDTO;
import com.negocio.stock.dto.LoginResultDTO;
import com.negocio.stock.model.UserSec;
import com.negocio.stock.repository.IUserSecRepository;
import com.negocio.stock.utils.CookieUtils;
import com.negocio.stock.utils.JwtUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserSecRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CookieUtils cookieUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSec userSec = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSec.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        userSec.getRoleList().stream()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNonExpired(),
                userSec.isCredentialsNonExpired(),
                userSec.isAccountNonLocked(),
                authorities
        );
    }

    public Authentication authenticate(String username, String password){
        UserDetails user = this.loadUserByUsername(username);
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("Invalid username or password.");
        }
        return new UsernamePasswordAuthenticationToken(username,null,user.getAuthorities());
    }

    public LoginResultDTO loginUser(@Valid AuthLoginRequestDTO request){
        Authentication authentication = this.authenticate(request.username(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String newJwt = jwtUtils.createToken(authentication);
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new LoginResultDTO(newJwt, authorities);
    }
}
