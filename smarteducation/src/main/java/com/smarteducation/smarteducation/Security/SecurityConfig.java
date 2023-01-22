package com.smarteducation.smarteducation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() { //descriptografa a senha
      return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //configurações para limitar o acesso para admin  e user
        http
           .csrf().disable()
           .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/process-login")
            .successForwardUrl("/login")
            .failureUrl("/login?error=true")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .and()
            .httpBasic();
         return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{ // método de configuração do gerenciador de autenticação
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //autentica a password descriptografando
    } //userdetailservice é uma classe de interface que foi sobrecarregada para receber as informações do usuario de uma forma va´lida com o banco de dados
    
}
