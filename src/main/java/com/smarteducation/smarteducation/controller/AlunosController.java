package com.smarteducation.smarteducation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smarteducation.smarteducation.model.CRUD.AlunosService;
import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.UsuarioRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Status;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@PreAuthorize("hasAnyRole('Aluno')")
@Controller
@RequestMapping("/aluno")
public class AlunosController {
    @Autowired
    AlunosService alunosService;
    @Autowired
    AlunosRepository alunosRepository;
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public Alunos alunoLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        return alunosRepository.findBycpf(cpf);
    }
    
    @GetMapping("/realizar-matricula")
    public String lista(Model model){
        List<Materias> materiasDisponiveis= this.alunosService.materiasDisponiveis(alunoLogado().getId()); // usar matricula logada
        model.addAttribute("materiasDisponiveis", materiasDisponiveis);
        return "alunos/matricular";
    }


    @GetMapping("/ver-matriculas")
    public String verMatriculas(Model model){
        List<Status> materiasAtuais = this.alunosService.verMatriculasAtuais(alunoLogado().getId());
        model.addAttribute("materias", materiasAtuais);
        model.addAttribute("chObrigatoria", this.alunosService.chObrigatoria(alunoLogado().getId()));
        model.addAttribute("chPendente", this.alunosService.chPendente(alunoLogado().getId()));
        model.addAttribute("aluno", alunoLogado());
        return "alunos/verMatriculas";
    }

    @GetMapping("/realizar-matricula/{id}")
    public String matricular(@PathVariable String id){
        this.alunosService.realizarMatricula(id, alunoLogado().getId());
        return "redirect:/aluno/ver-matriculas";
    }

    @GetMapping("/historico")
    public String historico(Model model){
        Alunos aluno = this.alunosService.buscarAlunoId(alunoLogado().getId());
        model.addAttribute("historico", aluno.getHistorico());
        return "alunos/historico";
    }

    @PostMapping("/alterarFoto")
    public String alterarFoto(@RequestParam String idUser, @RequestParam String imagem){
        Alunos aluno = alunoLogado();
        aluno.setLinkFoto(imagem);
        this.alunosRepository.save(aluno);
        return "redirect:/aluno/ver-matriculas";
    }
}
