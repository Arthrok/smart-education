package com.smarteducation.smarteducation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smarteducation.smarteducation.model.Repository.UsuarioRepository;
import com.smarteducation.smarteducation.model.Schema.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class AuthController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/login")
    public String login(){
        return "auth/login";
    }    

    @PostMapping("/login")
    public String redirect(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario user = this.usuarioRepository.findBycpf(cpf);
        String redirect;
        if (user.getCargos().get(0).equals("professor")){
            redirect = "redirect:/professor";
        } else if(user.getCargos().get(0).equals("Aluno")) {
            redirect = "redirect:/aluno/ver-matriculas";
        } else {
            redirect = "redirect:/coordenador";
        }
        return redirect;
    }

}
