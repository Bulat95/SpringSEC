package com.example.springSEC.config;

import com.example.springSEC.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.springSEC.model.MyUser;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        MyUser myUser = myUserRepository.findByLogin(userName);
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user " + userName);
        }
        if (!password.equals(myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}