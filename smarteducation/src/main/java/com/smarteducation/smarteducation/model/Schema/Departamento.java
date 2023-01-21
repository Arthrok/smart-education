package com.smarteducation.smarteducation.model.Schema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Departamento {
    @Id
    private String id;
    private String nome;
    private int codigo;
    private List<Materias> listaMaterias;
    private List<Professor> listaProfessores;



    public Departamento(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public Departamento() {
  
    }



    public Departamento(String id, String nome, int codigo, List<Professor> listaProfessores) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.listaProfessores = new ArrayList<>(listaProfessores);
    }

    public Departamento(String id, String nome, int codigo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
    }
    
    // public Departamento(String id, String nome, int codigo, List<Materias> listaMaterias) {
    //     this.id = id;
    //     this.nome = nome;
    //     this.codigo = codigo;
    //     this.listaMaterias = listaMaterias;
    // }

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
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public List<Materias> getListaMaterias() {
        return listaMaterias;
    }
    public void setListaMaterias(List<Materias> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public List<Professor> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Professor> listaProfessores) {
        this.listaProfessores = new ArrayList<>(listaProfessores);
    }

    

}
