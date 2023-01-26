package com.smarteducation.smarteducation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.smarteducation.smarteducation.model.CRUD.CoordenadorService;
import com.smarteducation.smarteducation.model.Repository.ProfessorRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Curso;
import com.smarteducation.smarteducation.model.Schema.Departamento;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Professor;
import com.smarteducation.smarteducation.model.Schema.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
@PreAuthorize("hasAnyRole('professor', 'coordenador')")
@RequestMapping("/coordenador")
public class CoordenadorController {
    @Autowired
    CoordenadorService coordenadorService;
    @Autowired
    ProfessorRepository professorRepository;

    public Professor coordenadorLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        return professorRepository.findBycpf(cpf);
    }

    @GetMapping("")
    public String painel(Model model){
        model.addAttribute("coordenador", coordenadorLogado());
        return "coordenador/painelGeral";
    }
    
    @GetMapping("/cadastrar-professor")
    public String cadProf(Model model){
        List<Departamento> listarDepartamentos = this.coordenadorService.lsitarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        return "coordenador/criarProfessor";
    }

    @PostMapping("/process-prof")
    public String createProf(Professor prof, Usuario user){
        this.coordenadorService.verificaCpfMatricula(prof.getCpf(), prof.getMatricula());
        this.coordenadorService.criaProfessorUser(prof, user);
        return "redirect:/coordenador";
    }


    @GetMapping("/ver-professor")
    public String verProf(Model model){
        List<Professor> listarProfessores = this.coordenadorService.listarProfessores();
        model.addAttribute("listarProfessores", listarProfessores);
        return "coordenador/verProfessores";
    }

    @GetMapping("/ver-professor/{id}")
    public String seila(@PathVariable("id") String id, Model model){
        Professor prof = this.coordenadorService.findId(id);
        model.addAttribute("prof", prof);
        List<Departamento> listarDepartamentos = this.coordenadorService.lsitarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        return "coordenador/editarProfessor";
    }

    @PostMapping("/process-edit-prof")
    public String edit(Professor professor){
        String antigoNome = this.coordenadorService.findId(professor.getId()).getNome();
        this.coordenadorService.alterar(professor);
        this.coordenadorService.atualizarMateria(antigoNome, professor.getNome());
        return "redirect:/coordenador/ver-professor";
    }
    
    @GetMapping("/ver-professor/delete/{id}")
    public String deleteProf(@PathVariable String id){
        this.coordenadorService.deletarProfessor(id);
        return "redirect:/coordenador/ver-professor";
    }

    @GetMapping("/ver-departamento")
    public String verDep(Model model){
        List<Departamento> listarDepartamentos = this.coordenadorService.lsitarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        return("coordenador/verDepartamento");
    }

    @GetMapping("/criar-departamento")
    public String criarDep(){
        return("coordenador/criarDepartamento");
    }

    @PostMapping("/process-dep")
    public String criarDept(Departamento departamento){
        this.coordenadorService.criarDepartamento(departamento);
        return "redirect:/coordenador/ver-departamento";
    }

    @GetMapping("/ver-departamento/{id}")
    public String depEsp(@PathVariable String id, Model model){;
        List<Materias> materias = this.coordenadorService.materiaDep(id);
        List<Professor> professores = this.coordenadorService.professores(id);
        model.addAttribute("professores", professores);
        model.addAttribute("materias", materias);
        return "coordenador/vermaisDepartamento";
    }

    @GetMapping("/ver-departamento/editar/{id}")
    public String editDep(@PathVariable String id, Model model){
        Departamento departamento = this.coordenadorService.findDepId(id);
        model.addAttribute("departamento", departamento);
        return "coordenador/editarDepartamento";
    }

    @PostMapping("/process-edit-dep")
    public String editDep(Departamento departamento){
        this.coordenadorService.modifyAttDep(departamento.getId(), departamento.getNome());
        this.coordenadorService.alterarDepartamento(departamento);
        return "redirect:/coordenador/ver-departamento";
    }

    @GetMapping("/ver-departamento/delete/{id}")
    public String delDep(@PathVariable String id){
        this.coordenadorService.deletarDepartamento(id);
        return "redirect:/coordenador/ver-departamento";
    }
    @GetMapping("/criar-materia")
    public String criarMat(Model model){
        List<Departamento> listarDepartamentos = this.coordenadorService.lsitarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        List<Professor> listarProfessores = this.coordenadorService.listarProfessores();
        model.addAttribute("listarProfessores", listarProfessores);
        List<Materias> listarMaterias = this.coordenadorService.listarMaterias();
        model.addAttribute("listarMaterias", listarMaterias);
        return "coordenador/criarMateria";
    }

    @PostMapping("/process-materia")
    public String criarMateria(Materias materia, @RequestParam String dia, @RequestParam String horario){
        List<String> dias = Arrays.stream(dia.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
        List<String> horarios = Arrays.stream(horario.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
        materia.setGrade(dias, horarios);
        this.coordenadorService.criarMateria(materia);
        return "redirect:/coordenador";
    }

    @GetMapping("/ver-materia")
    public String verMaterias(Model model){
        List<Materias> listaMaterias = this.coordenadorService.listarMaterias();
        model.addAttribute("listaMaterias", listaMaterias);
        return "coordenador/verMaterias";
    }

    @GetMapping("/materia/editar/{id}")
    public String editarMaterias(@PathVariable String id, Model model){
        Materias materia = this.coordenadorService.acharMateria(id);
        model.addAttribute("materia", materia);
        List<Departamento> listarDepartamentos = this.coordenadorService.lsitarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        List<Professor> listarProfessores = this.coordenadorService.listarProfessores();
        model.addAttribute("listarProfessores", listarProfessores);
        List<Materias> listarMaterias = this.coordenadorService.listarMaterias();
        model.addAttribute("listarMaterias", listarMaterias);
        return "coordenador/editarMateria";
    }

    @PostMapping("/process-edit-materia")
    public String editMat(Materias materia){
        this.coordenadorService.criarMateria(materia);
        return "redirect:/coordenador/ver-departamento";
    }

    @GetMapping("/materia/delete/{id}")
    public String deleteMateria(@PathVariable String id){
        this.coordenadorService.deleteMateria(id);
        return "redirect:/coordenador/ver-departamento";
    }

    @GetMapping("/criar-curso")
    public String criarCurso(Model model){
        List<Materias> listarMaterias = this.coordenadorService.listarMaterias();
        model.addAttribute("listarMaterias", listarMaterias);
        return "coordenador/criarCurso";
    }

    @PostMapping("/process-curso")
    public String procCurso(Curso curso){
        this.coordenadorService.criarCurso(curso);
        return "redirect:/coordenador";
    }


    @GetMapping("/ver-curso")
    public String verCurso(Model model){
        List<Curso> listaCursos = this.coordenadorService.listarCursos();
        model.addAttribute("listaCursos", listaCursos);
        return "coordenador/verCurso";
    }

    @GetMapping("/ver-curso/delete/{id}")
    public String delCurso(@PathVariable String id){
        this.coordenadorService.deletarCurso(id);
        return "redirect:/coordenador/ver-curso";
    }


    @GetMapping("/ver-curso/editar/{id}")
    public String editCurso(@PathVariable String id, Model model){
        model.addAttribute("curso", this.coordenadorService.cursoById(id));
        return "coordenador/editarCurso";
    }

    @PostMapping("/ver-curso/editar/process-edit-curso")
    public String editsCurso(Curso curso){
        this.coordenadorService.criarCurso(curso);
        return "redirect:/coordenador/ver-curso";
    }

    @GetMapping("/criar-aluno")
    public String criarAluno(Model model){
        model.addAttribute("cursos", this.coordenadorService.listarCursos());
        return "coordenador/criarAluno";
    }
    
    @PostMapping("/process-aluno")
    public String procAluno(Alunos alunos, @RequestParam String curso, Usuario user){
        this.coordenadorService.verificaCpfMatricula(alunos.getCpf(), alunos.getMatricula());
        alunos.setCurso(this.coordenadorService.cursoBynome(curso));
        this.coordenadorService.criarAlunoUser(alunos, user);
        return "redirect:/coordenador";
    }

}
