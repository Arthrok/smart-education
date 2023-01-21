package com.smarteducation.smarteducation.model.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smarteducation.smarteducation.model.Schema.Departamento;

public interface DepartamentoRepository extends MongoRepository<Departamento, String>{
    Departamento findBynome(String nomeDep);
    Departamento findByid(String id);
}
