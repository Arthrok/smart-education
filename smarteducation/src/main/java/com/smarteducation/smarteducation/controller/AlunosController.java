package com.smarteducation.smarteducation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smarteducation.smarteducation.model.CRUD.AlunosService;
import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/aluno")
public class AlunosController {
    @Autowired
    AlunosService alunosService;
    @Autowired
    AlunosRepository alunosRepository;
    @Autowired
    MateriaRepository materiaRepository;

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
        return "alunos/verMatriculas";
    }

    @GetMapping("/realizar-matricula/{id}")
    public String matricular(@PathVariable String id){
        Materias materia = this.alunosService.materiaById(id);
        Alunos aluno = this.alunosService.buscarAlunoId(alunoLogado().getId());
        Status materiaAtual = new Status();
        materiaAtual.setMaterias(materia);
        materiaAtual.setStatus(1);
        Materias materiaAtt = this.materiaRepository.findByid(materiaAtual.getMaterias().getId());
        materiaAtt.addAluno(aluno);
        this.materiaRepository.save(materiaAtt);
        aluno.addHistorico(materiaAtual);
        this.alunosService.salvarAluno(aluno);
        return "redirect:/aluno/ver-matriculas";
    }

    @GetMapping("/historico")
    public String historico(Model model){
        Alunos aluno = this.alunosService.buscarAlunoId(alunoLogado().getId());
        model.addAttribute("historico", aluno.getHistorico());
        return "alunos/historico";
    }

}
