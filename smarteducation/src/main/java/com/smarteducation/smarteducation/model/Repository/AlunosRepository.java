package com.smarteducation.smarteducation.model.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smarteducation.smarteducation.model.Schema.Alunos;

public interface AlunosRepository extends MongoRepository<Alunos, String>{
    Alunos findByid(String id);
    Alunos findBycpf(String cpf);
}
