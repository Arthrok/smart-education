package com.smarteducation.smarteducation.controller;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bspfsystems.simplejson.JSONObject;

import java.util.List;
import com.google.gson.*;
import com.google.gson.Gson;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smarteducation.smarteducation.model.CRUD.AlunosService;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Grade;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Status;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/aluno")
public class AlunosController {
    @Autowired
    AlunosService alunosService;



    @GetMapping("/realizar-matricula")
    public String lista(Model model){
        List<Materias> materiasDisponiveis= this.alunosService.materiasDisponiveis("63cbcb2c9d63980740f9406d"); // usar matricula logada
        model.addAttribute("materiasDisponiveis", materiasDisponiveis);
        return "alunos/matricular";
    }


    @GetMapping("/ver-matriculas")
    public String verMatriculas(Model model){
        String json = "{\"dia\":\"matematica\",\"horario\":\"123\"}";   
        Gson gson = new Gson();
        // System.out.println(jsonObject);
        // Grade grade1 = gson.fromJson(json, Grade.class);
        // System.out.println( jsonObject.get("horario"));
        List<Status> materiasAtuais = this.alunosService.verMatriculasAtuais("63cbcb2c9d63980740f9406d");
        model.addAttribute("materias", materiasAtuais);
        return "alunos/verMatriculas";
    }

    @GetMapping("/realizar-matricula/{id}")
    public String matricular(@PathVariable String id){
        Materias materia = this.alunosService.materiaById(id);
        Alunos aluno = this.alunosService.buscarAlunoId("63cbcb2c9d63980740f9406d");
        Status materiaAtual = new Status();
        materiaAtual.setMaterias(materia);
        materiaAtual.setStatus(1);
        aluno.addHistorico(materiaAtual);
        this.alunosService.salvarAluno(aluno);
        return "redirect:/aluno/ver-matriculas";
    }


}
