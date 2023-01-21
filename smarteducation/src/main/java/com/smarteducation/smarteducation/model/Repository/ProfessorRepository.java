package com.smarteducation.smarteducation.model.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smarteducation.smarteducation.model.Schema.Professor;

public interface ProfessorRepository extends MongoRepository<Professor, String>{
    @Query("{'departamento' : ?0}")
    List<Professor> findPositionalParameters(String departamento);
    
    Professor findByid(String id);
}
