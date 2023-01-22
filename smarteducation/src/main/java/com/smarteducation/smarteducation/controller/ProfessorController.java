package com.smarteducation.smarteducation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smarteducation.smarteducation.model.CRUD.ProfessorService;
import com.smarteducation.smarteducation.model.Schema.Materias;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;
    
    @GetMapping("")
    @ResponseBody
    public List<Materias> body(){

        return this.professorService.buscaPorProfessor("63c972abee8e8b1cbf6adbda");
    }







}
