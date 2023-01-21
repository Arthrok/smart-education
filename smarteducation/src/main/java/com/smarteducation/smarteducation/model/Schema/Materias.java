package com.smarteducation.smarteducation.model.Schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Materias {
    @Id
    private String id;
    private String nome;
    private String codigo;
    private List<String> requisito;
    private String professor;
    private int credito;
    private String turma;
    private String departamento;
    private int ano;
    private int semestre;
    private List<Grade> grades;


    

    public void setRequisito(List<String> requisito) {
        this.requisito = requisito;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Materias() {
    }

    public Materias(String id, String departamento) {
        this.id = id;
        this.departamento = departamento;
    }
    

    public Materias(String nome, String codigo, String requisito, String professor, int credito, String turma, String departamento, int ano, int semestre, JsonObject json) {
        this.codigo = codigo;
        this.nome = nome;
        this.credito = credito;
        this.turma = turma;
        this.departamento = departamento;
        this.ano = ano;
        this.semestre = semestre;
        this.professor = professor;
        this.requisito = Arrays.stream(requisito.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
    }
    
    public Materias(String id, String nome, String codigo, String requisito, String professor, int credito, String turma, String departamento,
            int ano, int semestre) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.requisito = Arrays.stream(requisito.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
        this.credito = credito;
        this.turma = turma;
        this.departamento = departamento;
        this.ano = ano;
        this.semestre = semestre;
    }

    



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public List<String> getRequisito() {
        return requisito;
    }
    public void setRequisito(String requisito) {
        this.requisito = Arrays.stream(requisito.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
    }
    public int getCredito() {
        return credito;
    }
    public void setCredito(int credito) {
        this.credito = credito;
    }
    public String getTurma() {
        return turma;
    }
    public void setTurma(String turma) {
        this.turma = turma;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public int getSemestre() {
        return semestre;
    }
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }




    
}
