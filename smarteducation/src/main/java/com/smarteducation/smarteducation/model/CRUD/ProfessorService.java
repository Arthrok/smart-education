package com.smarteducation.smarteducation.model.CRUD;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.ProfessorRepository;
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



}
