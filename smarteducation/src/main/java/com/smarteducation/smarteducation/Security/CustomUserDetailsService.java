package com.smarteducation.smarteducation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smarteducation.smarteducation.model.Repository.UsuarioRepository;
import com.smarteducation.smarteducation.model.Schema.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Usuario exitUser = this.usuarioRepository.findBycpf(cpf);
        if (exitUser == null) {
            throw new Error("Usuário não existe!");
        }

        return UserPrincipal.create(exitUser);
    }
    

}
