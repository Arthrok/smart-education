package com.smarteducation.smarteducation.model.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smarteducation.smarteducation.model.Schema.Materias;

public interface MateriaRepository extends MongoRepository<Materias, String>{
    @Query("{'departamento' : ?0}")
    List<Materias> departamentoFindBydepartamento(String departamento);

    @Query("{'professor' : ?0}")
    List<Materias> departamentoFindByprofessor(String professor);

    Materias findByid(String id);
    Materias findBynome(String nome);

    @Query("{'professor' : ?0, 'ativo' : ?1}")
    List<Materias> ativeFindByProfessor(String nome, boolean ativo);

}
