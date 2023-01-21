package com.smarteducation.smarteducation.model.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smarteducation.smarteducation.model.Schema.Materias;

public interface MateriaRepository extends MongoRepository<Materias, String>{
    @Query("{'departamento' : ?0}")
    List<Materias> departamentoFindBydepartamento(String departamento);

    Materias findByid(String id);
    Materias findBynome(String nome);

}
