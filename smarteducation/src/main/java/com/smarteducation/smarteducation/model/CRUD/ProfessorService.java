package com.smarteducation.smarteducation.model.CRUD;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.ProfessorRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Professor;

@Service
public class ProfessorService {
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    AlunosRepository alunosRepository;
    @Autowired
    ProfessorRepository professorRepository;
    
    public List<Materias> buscaPorProfessor(String id){
        Professor prof = this.professorRepository.findByid(id);
        return this.materiaRepository.ativeFindByProfessor(prof.getNome(), true);
    }

    public List<Alunos> buscarAlunosMateria(String id){
        Materias materia = this.materiaRepository.findByid(id);
        List<Alunos> emCurso = new ArrayList<>();
        materia.getAlunos().forEach(aluno -> {
            Alunos alune = this.alunosRepository.findByid(aluno.getId());
            for(int i = 0; i < alune.getHistorico().size(); i++){
                if(alune.getHistorico().get(i).getMaterias().getId().equals(materia.getId())){
                    if(alune.getHistorico().get(i).getStatus() == 1){
                        emCurso.add(alune);
                    }
                }
            }
        });
        return emCurso;
    }

    public void Avaliar(float nota, float frequencia, int status, String idAluno, String idMateria){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        for(int i = 0; i < aluno.getHistorico().size(); i++){
            if(aluno.getHistorico().get(i).getMaterias().getId().equals(idMateria)){
                aluno.getHistorico().get(i).setNota(nota);
                aluno.getHistorico().get(i).setFrequencia(frequencia);
                aluno.getHistorico().get(i).setStatus(status);
            }
        }
        this.alunosRepository.save(aluno);
    }



}
