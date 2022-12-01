package com.yeshtery.picturepublishing.security;

import com.yeshtery.picturepublishing.enums.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain config(HttpSecurity httpSecurity,
                                          JpaUserDetailsServers userDetailsServers) throws Exception {

        // Anonymous Access
        httpSecurity
                .authorizeRequests()
                .mvcMatchers("/image*", "/files/accept/*").permitAll()
                .mvcMatchers(HttpMethod.POST,"/registration").anonymous()
                .and()
                .csrf(x -> x.ignoringAntMatchers("/registration"));

        // User Access
        httpSecurity
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/image").hasRole(Authority.USER.name());


        // Admin Access
        httpSecurity
                .authorizeRequests()
                .mvcMatchers("/admin/image/**", "/files/unprocessed/*")
                .hasRole(Authority.ADMIN.name());
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        // Authentication Config
        httpSecurity.httpBasic()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .userDetailsService(userDetailsServers);
        // httpSecurity.csrf().disable();
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
