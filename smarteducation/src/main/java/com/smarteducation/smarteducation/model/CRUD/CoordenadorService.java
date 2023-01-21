package com.smarteducation.smarteducation.model.CRUD;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.CoordenadorRepository;
import com.smarteducation.smarteducation.model.Repository.CursoRepository;
import com.smarteducation.smarteducation.model.Repository.DepartamentoRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.ProfessorRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Curso;
import com.smarteducation.smarteducation.model.Schema.Departamento;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Professor;

@Service
public class CoordenadorService {
    @Autowired
    CoordenadorRepository coordenadorRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    AlunosRepository alunosRepository;


    public void criaProfessor(Professor prof){
        this.professorRepository.save(prof);
        this.atualizarDepartamento(prof.getDepartamento());
    }

    public List<Professor> listarProfessores(){
        return this.professorRepository.findAll();
    }

    // public Optional<User> findById(String id){
    //     Optional<User> exit = this.userRepository.findById(id);
    //     if (exit.isEmpty()){
    //         throw new Error("Usuario nao encontrado!");
    //     }
    //     return exit;
    // }

    public Professor findId(String id){
        Optional<Professor> prof = this.professorRepository.findById(id);
        Professor professor = new Professor();
        professor = prof.get();
        return professor;
    }

    public Professor alterar(Professor prof){
        return this.professorRepository.save(prof);
    }

    public void deletarProfessor(String id){
        String depart = this.professorRepository.findByid(id).getDepartamento();
        this.professorRepository.deleteById(id);
        this.atualizarDepartamento(depart);

    }

    public void criarDepartamento(Departamento departamento){
        this.departamentoRepository.save(departamento);
    }

    public Departamento alterarDepartamento(Departamento departamento){
        return this.departamentoRepository.save(departamento);
    }

    public void deletarDepartamento(String id){
        this.departamentoRepository.deleteById(id);
    }

    public List<Departamento> lsitarDepartamentos(){
        return this.departamentoRepository.findAll();
    }

    public Departamento findDepId(String id){
        Optional<Departamento> departamento = this.departamentoRepository.findById(id);
        Departamento depart = new Departamento();
        depart = departamento.get();
        return depart;
    }


    public List<Professor> professores(String departId){
        Departamento departament = findDepId(departId);     
        departament.setListaProfessores(this.professorRepository.findPositionalParameters(departament.getNome()));
        this.departamentoRepository.save(departament);
        return departament.getListaProfessores();
    }

    public void atualizarDepartamento(String nomeDep){
        List<Professor> listaAtt = this.professorRepository.findPositionalParameters(nomeDep);
        Departamento dep = this.departamentoRepository.findBynome(nomeDep);
        dep.setListaProfessores(listaAtt);
        this.departamentoRepository.save(dep);
    }


    public void criarMateria(Materias materia){
        this.materiaRepository.save(materia);
    }

    public List<Materias> listarMaterias(){
        return this.materiaRepository.findAll();
    }
    
    public List<Materias> materiaDep(String Id){
        Departamento dep = this.departamentoRepository.findByid(Id);
        return this.materiaRepository.departamentoFindBydepartamento(dep.getNome());
    }

    public Materias acharMateria(String id){
        return this.materiaRepository.findByid(id);
    }

    public void deleteMateria(String id){
        this.materiaRepository.deleteById(id);
    }

    public void criarCurso(Curso curso){
        this.cursoRepository.save(curso);
    }
    
    public List<Curso> listarCursos(){
        return cursoRepository.findAll();
    }

    public void deletarCurso(String id){
        this.cursoRepository.deleteById(id);
    }

    public Curso editCurso(String curso){
        return this.cursoRepository.findBynome(curso);
    }

    public Curso cursoById(String curso){
        return this.cursoRepository.findByid(curso);
    }
    public Curso cursoBynome(String curso){
        return this.cursoRepository.findBynome(curso);
    }

    public void modifyAttDep(String id, String departamento){ // modificar nome do departamento implica em modificar o nome do dep em todas as matérias que estavam contidas nele
        Departamento dep = this.departamentoRepository.findByid(id);
        List<Materias> materias = this.materiaRepository.departamentoFindBydepartamento(dep.getNome());
        for(int i = 0; i < materias.size(); i++){
            materias.get(i).setDepartamento(departamento);
            this.materiaRepository.save(materias.get(i));
        }
        List<Professor> professores = this.professorRepository.findPositionalParameters(dep.getNome());
        for(int i = 0; i < professores.size(); i++){
            professores.get(i).setDepartamento(departamento);
            this.professorRepository.save(professores.get(i));
        }
    }

    public void criarAluno(Alunos aluno){
        this.alunosRepository.save(aluno);
    }

}
