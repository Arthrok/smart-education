package com.smarteducation.smarteducation.model.Schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Curso {
    @Id
    private String id;
    private String nome;
    private int semestresMin;
    private int semestresMax;
    private List<String> estruturaCurricular;
    
    public Curso() {

    }

    public Curso(String nome, String estruturaCurricular, int semestresMin, int semestresMax) {
        this.nome = nome;
        this.estruturaCurricular = Arrays.stream(estruturaCurricular.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
        this.semestresMax = semestresMax;
        this.semestresMin = semestresMin;
    }
    
    
    public int getSemestresMin() {
        return semestresMin;
    }

    public void setSemestresMin(int semestresMin) {
        this.semestresMin = semestresMin;
    }

    public int getSemestresMax() {
        return semestresMax;
    }

    public void setSemestresMax(int semestresMax) {
        this.semestresMax = semestresMax;
    }

    public Curso(String id, String nome, String estruturaCurricular) {
        this.id = id;
        this.nome = nome;
        this.estruturaCurricular = Arrays.stream(estruturaCurricular.replaceAll(",", "\n").split("\n")).collect(Collectors.toList());
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<String> getEstruturaCurricular() {
        return estruturaCurricular;
    }
    public void setEstruturaCurricular(List<String> estruturaCurricular) {
        this.estruturaCurricular = new ArrayList<>(estruturaCurricular);
    }

    

    




}
