package com.smarteducation.smarteducation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smarteducation.smarteducation.model.CRUD.AlunosService;
import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.Model;



@Controller
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    AlunosService alunosService;
    @Autowired 
    AlunosRepository alunosRepository;

    public Alunos alunoLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        return alunosRepository.findBycpf(cpf);
    }


    @GetMapping("/criar-prato")
    public String body(Model model){
        model.addAttribute("horarios", this.alunosService.listaHorarios(alunoLogado().getId()));
        return "restaurante/criarPrato";
    }


}
