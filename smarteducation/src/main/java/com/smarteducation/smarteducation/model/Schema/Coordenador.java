package com.smarteducation.smarteducation.model.Schema;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Coordenador extends Usuario{
    public Coordenador(String nome, long matricula, String cpf, String email, String senha, String dataNascimento){
        super(nome, matricula, cpf, email, senha, dataNascimento);
    }
}
