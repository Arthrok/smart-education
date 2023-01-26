package com.smarteducation.smarteducation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smarteducation.smarteducation.model.CRUD.ProfessorService;
import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.ProfessorRepository;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Professor;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/professor")
@PreAuthorize("hasAnyRole('professor')")
public class ProfessorController {
    @Autowired
    ProfessorService professorService;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    AlunosRepository alunosRepository;
    @Autowired
    MateriaRepository materiaRepository;

    public Professor professorLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        return professorRepository.findBycpf(cpf);
    }
    
    @GetMapping("")
    public String body(Model model){
        model.addAttribute("materias", this.professorService.buscaPorProfessor(professorLogado().getId()));
        model.addAttribute("professor", professorLogado());
        return "professor/visaoGeral";
    }

    @GetMapping("/materia-acess/{id}")
    public String verMateria(@PathVariable String id, Model model){
        model.addAttribute("alunos", this.professorService.buscarAlunosMateria(id));
        model.addAttribute("idMateria", id);
        return "professor/verMateria";
    }

    @GetMapping("/materia-acess/{idMateria}/{idAluno}")
    public String avaliarAluno(@PathVariable String idMateria, @PathVariable String idAluno, Model model){
        model.addAttribute("aluno", this.alunosRepository.findByid(idAluno));
        model.addAttribute("materia", this.materiaRepository.findByid(idMateria));
        return "professor/avaliarAluno";
    }

    @PostMapping("/materia-acess/avaliar")
    public String procAval(@RequestParam float nota, @RequestParam float frequencia, @RequestParam int status, @RequestParam String idAluno, @RequestParam String idMateria){
        this.professorService.Avaliar(nota, frequencia, status, idAluno, idMateria);
        String redirect = "redirect:/professor/materia-acess/".concat(idMateria);
        return redirect;
    }

    @GetMapping("/materia-acess/desativar/{id}")
    public String desativarMateria(@PathVariable String id){
        Materias materia = this.materiaRepository.findByid(id);
        materia.setAtivo(false);
        this.materiaRepository.save(materia);
        return "redirect:/professor";
    }

    @GetMapping("/historico")
    public String historico(Model model){
        model.addAttribute("materias", this.professorService.materiaInativa(professorLogado().getId()));
        return "professor/historico";
    }

    @GetMapping("/historico/{id}")
    public String verHistorico(Model model, @PathVariable String id){
        model.addAttribute("alunos", this.professorService.historicoMateria(id));
        model.addAttribute("materia", this.materiaRepository.findByid(id));
        return "professor/verHistoricoMateria";
    }





}
