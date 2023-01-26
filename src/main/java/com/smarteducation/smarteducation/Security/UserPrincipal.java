package com.smarteducation.smarteducation.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smarteducation.smarteducation.model.Schema.Usuario;

public class UserPrincipal implements UserDetails{

    private String cpf; // cpf;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Usuario user){
        this.cpf = user.getCpf();
        this.senha = user.getSenha();
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities = user.getCargos().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_".concat(role));
        }).collect(Collectors.toList());
        this.authorities = authorities;
    }

    public static UserPrincipal create(Usuario user){
        return new UserPrincipal(user);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
