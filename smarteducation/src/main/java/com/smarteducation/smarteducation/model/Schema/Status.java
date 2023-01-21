package com.smarteducation.smarteducation.model.Schema;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Status {
    private Materias materias; //nao repetir materia
    private float frequencia;
    private float nota;
    private int status; // 1: cursando - 2: aprovado - 3: reprovado

    
    public Status() {
    }

    public Status(Materias materias, float frequencia, float nota, int status) {
        this.materias = materias;
        this.frequencia = frequencia;
        this.nota = nota;
        this.status = status;
    }
    public Materias getMaterias() {
        return materias;
    }
    public void setMaterias(Materias materias) {
        this.materias = materias;
    }
    public float getFrequencia() {
        return frequencia;
    }
    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }
    public float getNota() {
        return nota;
    }
    public void setNota(float nota) {
        this.nota = nota;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }






}
