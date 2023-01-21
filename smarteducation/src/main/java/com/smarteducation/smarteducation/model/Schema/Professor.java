package com.smarteducation.smarteducation.model.Schema;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;



public class Professor extends Usuario{
    private List<String> especialidades;
    private List<String> materias;
    private String departamento;
    

    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public Professor(){

    }
    public Professor(String nome, long matricula, String cpf, String email, String senha, String dataNascimento, String especialidades, String departamento){
        super(nome, matricula, cpf, email, senha, dataNascimento);
        this.especialidades = Arrays.stream(especialidades.split("\n")).collect(Collectors.toList());
        this.departamento = departamento;
    }
    public Professor(String id, String nome, long matricula, String cpf, String email, String senha, String dataNascimento){
        super(id, nome, matricula, cpf, email, senha, dataNascimento);
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }
    public void setEspecialidades(String especialidades) {
        this.especialidades = Arrays.stream(especialidades.split("\n")).collect(Collectors.toList());
    }
    public List<String> getMaterias() {
        return materias;
    }
    public void setMaterias(List<String> materias) {
        this.materias = materias;
    }

    
}
