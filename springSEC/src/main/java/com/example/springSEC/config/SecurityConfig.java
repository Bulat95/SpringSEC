package com.example.springSEC.config;

import com.example.springSEC.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;
    private CustomAuthencationProvider customAuthencationProvider;

    @Autowired
    SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthencationProvider customAuthencationProvider) {
        this.userDetailsService = customUserDetailsService;
        this.customAuthencationProvider = customAuthencationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
          auth.authenticationProvider(customAuthencationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and().formLogin();

    }
}