package com.smarteducation.smarteducation.model.Schema;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Alunos extends Usuario{
    private Curso curso;
    private int semestre;
    private List<Status> materias = new ArrayList<>();
    private List<Status> historico = new ArrayList<>();

    
    public Alunos() {

    }


    public Alunos(String nome, long matricula, String cpf, String email, String senha, String dataNascimento,
            String curso, int semestre) {
        super(nome, matricula, cpf, email, senha, dataNascimento);
        this.semestre = semestre;
    }
    public Alunos(String id, String nome, long matricula, String cpf, String email, String senha, String dataNascimento) {
        super(id, nome, matricula, cpf, email, senha, dataNascimento);
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public int getSemestre() {
        return semestre;
    }
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    public List<Status> getMaterias() {
        return materias;
    }
    public void setMaterias(List<Status> materias) {
        this.materias = new ArrayList<>(materias);
    }

    public void addMaterias(Status materia){
        this.materias.add(materia);
    }


    public List<Status> getHistorico() {
        return historico;
    }


    public void setHistorico(List<Status> historico) {
        this.historico = new ArrayList<>(historico);
    }

    public void addHistorico(Status historicos) {
        this.historico.add(historicos);
    }


}
