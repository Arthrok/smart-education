package com.smarteducation.smarteducation.model.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smarteducation.smarteducation.model.Schema.Curso;

public interface CursoRepository extends MongoRepository<Curso, String>{
    Curso findBynome(String nome);
    Curso findByid(String id);
}
