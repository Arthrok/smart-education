package com.smarteducation.smarteducation.model.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smarteducation.smarteducation.model.Schema.Coordenador;

public interface CoordenadorRepository extends MongoRepository<Coordenador, String>{
    
}
