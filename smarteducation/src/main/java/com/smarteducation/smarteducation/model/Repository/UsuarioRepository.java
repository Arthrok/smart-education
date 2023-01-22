package com.smarteducation.smarteducation.model.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smarteducation.smarteducation.model.Schema.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
    Usuario findBycpf(String cpf);
    Usuario findByid(String id);
    Usuario findBynome(String nome);
}
