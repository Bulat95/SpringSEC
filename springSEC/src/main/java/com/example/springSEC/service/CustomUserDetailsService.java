package com.example.springSEC.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.springSEC.model.MyUser;
import com.example.springSEC.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MyUserRepository dao;

    Logger logger = LoggerFactory.getLogger(MyUserRepository.class);

    @Autowired
    CustomUserDetailsService (MyUserRepository repository){
        this.dao = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser myUser= dao.findByLogin(userName);
        logger.info(myUser.getLogin());
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        UserDetails user = User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
}
